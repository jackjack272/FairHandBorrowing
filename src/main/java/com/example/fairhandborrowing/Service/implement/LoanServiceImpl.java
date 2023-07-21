package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.DTO.LoanDto;
import com.example.fairhandborrowing.Model.Loan;
import com.example.fairhandborrowing.Repository.LoanRepository;
import com.example.fairhandborrowing.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDto> getAllLoansByUserId(Long userId) {

        List<Loan> loans = loanRepository.findAllByUserUserId(userId).get();
        return loans.stream().map(loan -> mapToDto(loan)).collect(Collectors.toList());
    }

    private LoanDto mapToDto(Loan loan) {
    LoanDto loanDto = LoanDto.builder()
            .id(loan.getId())
            .amountBorrowed(loan.getAmountBorrowed())
            .interestRate(loan.getInterestRate())
            .expectedEndDate(loan.getExpectedEndDate())
            .createdOn(loan.getCreatedOn())
            .user(loan.getUser())
            .build();

    return loanDto;
    }
}
