package com.example.fairhandborrowing.dto;

import com.example.fairhandborrowing.model.Contract;
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
    private List<String> lenderFundAmount;

    private Double interestRate;

    private Integer term;

    private String startDate;

    private List<Contract> contracts;

    private boolean signed;
}
