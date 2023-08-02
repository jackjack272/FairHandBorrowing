package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanFundsRepository extends JpaRepository<LoanFunds, Long> {

    List<LoanFunds> findByAcceptedAndLender(boolean accepted, UserEntity user);

    List<LoanFunds> findByLoanId(Long loan_id);

    List<LoanFunds> findByAcceptedAndLoanId(boolean accepted, Long loan_id);
}
