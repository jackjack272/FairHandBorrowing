package com.example.fairhandborrowing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContractDto {

    private String borrowerNameAddress;
    private List<String> lenderNameAddress;
    private List<Double> lenderFundAmount;

    private Double interestRate;

    private Integer term;

    private String startDate;
}
