package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findContractByLoanFundLoanFundId(Long loanFundId);
}
