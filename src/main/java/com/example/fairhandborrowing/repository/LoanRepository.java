package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long>
{
    Optional<List<Loan>> findAllByUserUserId(Long userId);
}
