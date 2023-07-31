package com.example.fairhandborrowing.mapper;

import com.example.fairhandborrowing.dto.LoanDto;
import com.example.fairhandborrowing.model.Loan;

import java.util.stream.Collectors;

public class LoanMapper {

    public static Loan mapToModel(LoanDto loanDto) {
        Loan loan = Loan.builder()
                .amountBorrowed(loanDto.getAmountBorrowed())
                .interestRate(loanDto.getInterestRate())
                .monthsToPay(loanDto.getMonthsToPay())
                .collaterals(loanDto.getCollaterals())
                .build();

        return loan;
    }

    public static LoanDto mapToDto(Loan loan) {
    LoanDto loanDto =
        LoanDto.builder()
            .id(loan.getId())
            .amountBorrowed(loan.getAmountBorrowed())
            .interestRate(loan.getInterestRate())
            .monthsToPay(loan.getMonthsToPay())
            .createdOn(loan.getCreatedOn())
            .collaterals(loan.getCollaterals())
            .user(loan.getUser())
            .build();

    return loanDto;
    }
}
