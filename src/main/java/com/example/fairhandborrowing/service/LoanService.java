package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.LoanDto;

import java.util.List;

public interface LoanService {
    List<LoanDto> getAllLoansByUserId(Long userId);

    LoanDto createLoan(String userName, LoanDto loanDto);
}
