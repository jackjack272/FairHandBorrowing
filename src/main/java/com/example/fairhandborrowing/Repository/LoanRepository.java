package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long>
{
    Optional<List<Loan>> findAllByUserUserId(Long userId);
}
