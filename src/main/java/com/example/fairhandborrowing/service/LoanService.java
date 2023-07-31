package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getAllLoansByUserId(Long userId);

    void createLoan(String userName, LoanDto loanDto);
}
