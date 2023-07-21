package com.example.fairhandborrowing.Service;

import com.example.fairhandborrowing.DTO.LoanDto;
import com.example.fairhandborrowing.Model.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDto> getAllLoansByUserId(Long userId);
}
