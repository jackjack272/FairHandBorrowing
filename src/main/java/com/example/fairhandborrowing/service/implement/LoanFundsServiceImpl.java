package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.*;
import com.example.fairhandborrowing.model.constants.Constants;
import com.example.fairhandborrowing.repository.*;
import com.example.fairhandborrowing.service.LoanFundsService;
import com.example.fairhandborrowing.service.TransactionService;
import com.example.fairhandborrowing.service.UserService;
import jakarta.transaction.Transactional;
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
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private LoanStatusRepository loanStatusRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Override
    public void sendFundRequest(UserEntity borrower, UserEntity lender, Loan loan) {
        LoanFunds fund = new LoanFunds();
        fund.setBorrower(borrower);
        fund.setLender(lender);
        fund.setLoan(loan);
        fund.setAccepted(false);
        fund.setRejected(false);
        fund.setFundAmount(0.0);

        loanFundsRepository.save(fund);
    }

    @Override
    public List<LoanFunds> getPendingRequestsForUser(UserEntity user) {
        return loanFundsRepository.findByAcceptedAndLenderAndRejected(false, user, false);
    }

    @Override
    @Transactional
    public void acceptFundRequest(Long fundLoanId, Double fundAmount) {
        LoanFunds loanFunds = loanFundsRepository.findById(fundLoanId).get();
        loanFunds.setAccepted(true);
        loanFunds.setFundAmount(fundAmount);

        Loan loan = loanFunds.getLoan();

        UserEntity lender = loanFunds.getLender();
        lender.setAvailableFunds(lender.getAvailableFunds().subtract(BigDecimal.valueOf(fundAmount)));
        lender.setFundsOnHold(lender.getFundsOnHold().add(BigDecimal.valueOf(fundAmount)));
        userService.updateUser(lender);

        UserEntity borrower = loanFunds.getBorrower();
        borrower.setFundsOnHold(borrower.getFundsOnHold().add(BigDecimal.valueOf(fundAmount)));
        userService.updateUser(borrower);


        transactionService.sendMoneyUserToUser(lender, borrower, BigDecimal.valueOf(fundAmount), loan,
                transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_LEND));
        loanFundsRepository.save(loanFunds);

        // check if loan is fully funded then release hold
        Double amountBorrowed = loan.getAmountBorrowed();
        Double totalFunded = calculateTotalFunded(loan.getId());

        if(totalFunded >= amountBorrowed) {
            BigDecimal transferFundToAvailable = BigDecimal.valueOf(totalFunded);
            borrower.setFundsOnHold(borrower.getFundsOnHold().subtract(transferFundToAvailable));
            borrower.setAvailableFunds(borrower.getAvailableFunds().add(transferFundToAvailable));
            userService.updateUser(borrower);

            transactionService.sendMoneyUserToUser(null, borrower, transferFundToAvailable, loan,
                    transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_RELEASE_HOLD));

            List<LoanFunds> lfs = loanFundsRepository.findByLoanId(loan.getId());
            lfs.stream().forEach(lf -> {
                UserEntity mlender = lf.getLender();
                Double amountLent = lf.getFundAmount();

                mlender.setFundsOnHold(mlender.getFundsOnHold().subtract(BigDecimal.valueOf(amountLent)));
                userService.updateUser(mlender);

                transactionService.sendMoneyUserToUser(mlender, borrower, BigDecimal.valueOf(amountLent), loan,
                        transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_RELEASE_HOLD));


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

    @Override
    public List<LoanFunds> getAcceptedRequestsForUser(UserEntity user) {
        return loanFundsRepository.findByAcceptedAndLender(true, user);
    }

    @Override
    public void rejectFundRequest(Long fundLoanId) {
        LoanFunds loanFunds = loanFundsRepository.findById(fundLoanId).get();

        loanFunds.setRejected(true);
        loanFundsRepository.save(loanFunds);
    }

}
