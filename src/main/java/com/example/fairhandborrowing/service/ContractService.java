package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.LoanFunds;

import java.util.List;

public interface ContractService {
    void signContract(String userName, Long loanId);
}
