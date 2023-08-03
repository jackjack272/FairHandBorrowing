package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Contract;
import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.LoanFunds;
import com.example.fairhandborrowing.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface LoanFundsRepository extends JpaRepository<LoanFunds, Long> {

    List<LoanFunds> findByAcceptedAndLender(boolean accepted, UserEntity user);

    List<LoanFunds> findByAcceptedAndLenderAndRejected(boolean accepted, UserEntity user, boolean rejected);


    List<LoanFunds> findByLoanId(Long loan_id);

    List<LoanFunds> findByRejectedAndLoanId(boolean rejected, Long loanId);

    List<LoanFunds> findByAcceptedAndLoanId(boolean accepted, Long loan_id);

    List<LoanFunds> findByLenderAndAcceptedAndLoanId(UserEntity lender, boolean accepted, Long loan_id);
    List<LoanFunds> findByBorrowerAndAcceptedAndLoanId(UserEntity borrower, boolean accepted, Long loan_id);
}
