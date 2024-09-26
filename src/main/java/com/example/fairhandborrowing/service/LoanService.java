package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.UserEntity;

import java.util.List;

public interface LoanService {
    List<Loan> getAllLoansByUserId(Long userId);

    List<Loan> getAllNonArchivedLoansByUserId(Long userId);

    List<Loan> getAllCompletedAndArchivedLoansByUserId(Long userId);

    Loan getLoanById(Long loanId);

    void createLoan(String userName, LoanDto loanDto);

    void editLoan(Long loanId, LoanDto loanDto, String[] collaterals);

    void archiveLoan(Long loanId);

    void prepareDtos(List<LoanDto> loanDtos, List<Loan> loans, UserEntity user);
}
