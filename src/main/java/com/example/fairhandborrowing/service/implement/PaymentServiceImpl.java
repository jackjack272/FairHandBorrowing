package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.model.constants.Constants;
import com.example.fairhandborrowing.repository.*;
import com.example.fairhandborrowing.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private LoanFundsRepository loanFundsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public void generatePaymentSchedule(Loan loan) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        List<Payment> paymentRecords = new ArrayList<>();
        int paymentRecordCount = loan.getMonthsToPay();
        double amountDue = loan.getAmountBorrowed()/paymentRecordCount;
        double interestDue = amountDue * loan.getInterestRate() / 100;
        double totalDue = amountDue + interestDue;
        boolean paid = false;

        for(int i = 0; i < paymentRecordCount; i++) {
            calendar.add(Calendar.MONTH, 1);
            Payment.PaymentBuilder paymentBuilder = Payment.builder();
            paymentBuilder.dueDate(new Timestamp(calendar.getTime().getTime()));

            paymentBuilder.amountDue(amountDue);
            paymentBuilder.interestDue(interestDue);
            paymentBuilder.totalDue(totalDue);
            paymentBuilder.paid(paid);
            paymentBuilder.loan(loan);
            paymentRecords.add(paymentBuilder.build());
            paymentRepository.save(paymentBuilder.build());
        }

        loan.setPayments(paymentRecords);
        loanRepository.save(loan);
    }

    @Override
    public void processPayment(Long paymentId) {
        Optional<Payment> paymentOp = paymentRepository.findById(paymentId);
        Payment payment = paymentOp.get();
        Loan loan = payment.getLoan();

        loan.getUser().setAvailableFunds(loan.getUser().getAvailableFunds().subtract(BigDecimal.valueOf(payment.getTotalDue())));

        payment.setPaymentDate(new Timestamp(new Date().getTime()));
        payment.setPaid(true);
        paymentRepository.save(payment);

        List<LoanFunds> lfs = loanFundsRepository.findByAcceptedAndLoanId(true, loan.getId());

        lfs.stream().forEach(lf -> {
            double amountDueForLender = lf.getFundAmount() / loan.getMonthsToPay();
            double interestDueForLender = amountDueForLender * loan.getInterestRate() / 100;
            double totalAmountDueForLender = amountDueForLender + interestDueForLender;

            lf.setFundOwed(lf.getFundOwed() - totalAmountDueForLender);
            transactionService.sendMoneyUserToUser(lf.getBorrower(), lf.getLender(), BigDecimal.valueOf(totalAmountDueForLender),
                    loan, transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_PAY));

            lf.getLender().setAvailableFunds(lf.getLender().getAvailableFunds().add(BigDecimal.valueOf(totalAmountDueForLender)));
            loanFundsRepository.save(lf);
        });


        AtomicBoolean fullyPaid = new AtomicBoolean(true);
        // check if payments are all paid
        List<Payment> payments = loan.getPayments();
        payments.stream().forEach(p -> {
            fullyPaid.set(fullyPaid.get() && p.isPaid());
        });

        if(fullyPaid.get()) loan.setLoanStatus(loanStatusRepository.findLoanStatusByStatusName(Constants.COMPLETED));
        loanRepository.save(loan);
    }

    @Override
    public String getPaidCountStr(List<Payment> payments) {
        AtomicReference<Double> totalPaid = new AtomicReference<>(0.0);
        payments.stream().forEach(p -> {
            if(p.isPaid()) totalPaid.updateAndGet(v -> v + 1);
        });
        return String.valueOf(totalPaid.get()/(double)payments.size() * 100);
    }
}
