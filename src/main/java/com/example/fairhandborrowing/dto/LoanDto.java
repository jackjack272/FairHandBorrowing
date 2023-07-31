package com.example.fairhandborrowing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;

    private Double amountBorrowed;

    private Double interestRate;

    private int monthsToPay;

    private Date createdOn;

    private UserRegistrationDto user;

    private List<CollateralDto> collaterals;
}
