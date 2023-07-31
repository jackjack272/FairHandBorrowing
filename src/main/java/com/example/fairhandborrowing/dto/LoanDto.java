package com.example.fairhandborrowing.dto;

import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.UserEntity;
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

    private UserEntity user;

    private List<Collateral> collaterals;
}
