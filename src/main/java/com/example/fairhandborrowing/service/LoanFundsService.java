package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.TransactionType;
import com.example.fairhandborrowing.model.UserEntity;

import java.util.List;

public interface LoanFundsService {
    void sendFundRequest(UserEntity borrower, UserEntity lender , Loan loan);

    List<LoanFunds> getPendingRequestsForUser(UserEntity user);

    void acceptFundRequest(Long fundLoanId, Double fundAmount);

    Double calculateLoanFundingProgress(LoanDto loanDto);

    Double calculateTotalFunded(Long loanId);

    List<LoanFunds> getAcceptedRequestsForUser(UserEntity user);

    void rejectFundRequest(Long fundLoanId);
}
