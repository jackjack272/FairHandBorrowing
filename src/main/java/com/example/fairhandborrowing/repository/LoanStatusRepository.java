package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.LoanStatus;
import com.example.fairhandborrowing.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanStatusRepository extends JpaRepository<LoanStatus, Long> {

    LoanStatus findLoanStatusByStatusName(String statusName);
}
