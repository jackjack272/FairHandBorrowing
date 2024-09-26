package com.example.fairhandborrowing.dto;

import com.example.fairhandborrowing.model.LoanFunds;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanFundDto {
    private LoanFunds loanFund;
    private Double remainingFundsRequired;
}
