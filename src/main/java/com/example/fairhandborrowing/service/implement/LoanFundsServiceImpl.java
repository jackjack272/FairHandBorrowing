package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.model.constants.Constants;
import com.example.fairhandborrowing.repository.*;
import com.example.fairhandborrowing.service.LoanFundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class LoanFundsServiceImpl implements LoanFundsService {

    @Autowired
    private LoanFundsRepository loanFundsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendFundRequest(UserEntity borrower, UserEntity lender, Loan loan) {
        LoanFunds fund = new LoanFunds();
        fund.setBorrower(borrower);
        fund.setLender(lender);
        fund.setLoan(loan);
        fund.setAccepted(false);
        fund.setFundAmount(0.0);

        loanFundsRepository.save(fund);
    }

    @Override
    public List<LoanFunds> getPendingRequestsForUser(UserEntity user) {
        return loanFundsRepository.findByAcceptedAndLender(false, user);
    }

    @Override
    public void acceptFundRequest(Long fundLoanId, Double fundAmount) {
        LoanFunds loanFunds = loanFundsRepository.findById(fundLoanId).get();
        loanFunds.setAccepted(true);
        loanFunds.setFundAmount(fundAmount);

        Loan loan = loanFunds.getLoan();

        UserEntity lender = loanFunds.getLender();
        lender.setAvailableFunds(lender.getAvailableFunds().subtract(BigDecimal.valueOf(fundAmount)));
        lender.setFundsOnHold(lender.getFundsOnHold().add(BigDecimal.valueOf(fundAmount)));
        userRepository.save(lender);

        UserEntity borrower = loanFunds.getBorrower();
        borrower.setFundsOnHold(borrower.getFundsOnHold().add(BigDecimal.valueOf(fundAmount)));
        userRepository.save(borrower);

        Transaction.TransactionBuilder transactionBuilder = Transaction.builder();
        transactionBuilder.transactionType(transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_LEND));
        transactionBuilder.initiator(lender);
        transactionBuilder.recipient(borrower);
        transactionBuilder.amount(BigDecimal.valueOf(fundAmount));
        transactionBuilder.loan(loan);
        transactionRepository.save(transactionBuilder.build());
        loanFundsRepository.save(loanFunds);

        // check if loan is fully funded then release hold
        Double amountBorrowed = loan.getAmountBorrowed();
        Double totalFunded = calculateTotalFunded(loan.getId());

        if(totalFunded >= amountBorrowed) {
            BigDecimal transferFundToAvailable = BigDecimal.valueOf(totalFunded);
            borrower.setFundsOnHold(borrower.getFundsOnHold().subtract(transferFundToAvailable));
            borrower.setAvailableFunds(borrower.getAvailableFunds().add(transferFundToAvailable));
            userRepository.save(borrower);

            Transaction.TransactionBuilder bTransactionBuilder = Transaction.builder();
            bTransactionBuilder.transactionType(transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_RELEASE_HOLD));
            bTransactionBuilder.initiator(null);
            bTransactionBuilder.recipient(borrower);
            bTransactionBuilder.amount(transferFundToAvailable);
            bTransactionBuilder.loan(loan);
            transactionRepository.save(bTransactionBuilder.build());


            List<LoanFunds> lfs = loanFundsRepository.findByLoanId(loan.getId());
            lfs.stream().forEach(lf -> {
                UserEntity mlender = lf.getLender();
                Double amountLent = lf.getFundAmount();

                mlender.setFundsOnHold(mlender.getFundsOnHold().subtract(BigDecimal.valueOf(amountLent)));
                userRepository.save(mlender);

                Transaction.TransactionBuilder lTransactionBuilder = Transaction.builder();
                lTransactionBuilder.transactionType(transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_RELEASE_HOLD));
                lTransactionBuilder.initiator(mlender);
                lTransactionBuilder.recipient(borrower);
                lTransactionBuilder.amount(BigDecimal.valueOf(amountLent));
                lTransactionBuilder.loan(loan);
                transactionRepository.save(lTransactionBuilder.build());

                Contract.ContractBuilder contractBuilder = Contract.builder();
                contractBuilder.borrowerSigned(false);
                contractBuilder.lenderSigned(false);
                contractBuilder.loanFund(lf);

                lf.setContract(contractBuilder.build());
                loanFundsRepository.save(lf);
            });

            loan.setLoanStatus(loanStatusRepository.findLoanStatusByStatusName(Constants.FULLY_FUNDED));
            loanRepository.save(loan);

        }
    }

    @Override
    public Double calculateLoanFundingProgress(LoanDto loanDto) {
        List<LoanFunds> loanFunds = loanFundsRepository.findByLoanId(loanDto.getId());
        Double totalFundingNeeded = loanDto.getAmountBorrowed();
        AtomicReference<Double> totalFunded = new AtomicReference<>(0.0);

        loanFunds.stream().forEach(lf -> {
            if(lf.getFundAmount() != null)
                totalFunded.updateAndGet(v -> v + lf.getFundAmount());
        });

        if(totalFunded.get() == 0.0 || totalFundingNeeded == 0.0) return totalFunded.get();

        return totalFunded.get()/totalFundingNeeded;
    }

    @Override
    public Double calculateTotalFunded(Long loanId) {
        List<LoanFunds> loanFunds = loanFundsRepository.findByLoanId(loanId);
        AtomicReference<Double> totalFunded = new AtomicReference<>(0.0);

        loanFunds.stream().forEach(loanFund -> {
            totalFunded.updateAndGet(v -> v + loanFund.getFundAmount());
        });

        return totalFunded.get();
    }
}
