package com.example.fairhandborrowing.mapper;

import com.example.fairhandborrowing.DTO.LoanDto;
import com.example.fairhandborrowing.Model.Loan;

import java.util.stream.Collectors;

public class LoanMapper {

    public static Loan mapToModel(LoanDto loanDto) {
        Loan loan = Loan.builder()
                .amountBorrowed(loanDto.getAmountBorrowed())
                .interestRate(loanDto.getInterestRate())
                .expectedEndDate(loanDto.getExpectedEndDate())
                .collateralList(loanDto.getCollaterals().stream().map(collateralDto -> CollateralMapper.mapToModel(collateralDto)).collect(Collectors.toList()))
                .build();

        return loan;
    }

    public static LoanDto mapToDto(Loan loan) {
    LoanDto loanDto =
        LoanDto.builder()
            .id(loan.getId())
            .amountBorrowed(loan.getAmountBorrowed())
            .interestRate(loan.getInterestRate())
            .expectedEndDate(loan.getExpectedEndDate())
            .collaterals(loan.getCollateralList().stream().map(collateral -> CollateralMapper.mapToDto(collateral)).collect(Collectors.toList()))
            .user(UserMapper.mapToDto(loan.getUser()))
            .build();

    return loanDto;
    }
}
