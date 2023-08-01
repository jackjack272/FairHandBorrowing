package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.UserEntity;

import java.util.List;

public interface LoanFundsService {
    void sendFundRequest(UserEntity borrower, UserEntity lender , Loan loan);
    void acceptFundRequest(LoanFunds fund);

    List<LoanFunds> getPendingRequestsForUser(UserEntity user);
}
