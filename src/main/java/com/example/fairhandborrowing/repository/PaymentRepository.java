package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>
{
    List<Payment> findPaymentsByLoanId(Long loanId);
}
