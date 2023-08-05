package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.Payment;

import java.util.List;

public interface PaymentService {
    void generatePaymentSchedule(Loan loan);

    void processPayment(Long paymentId);

    String getPaidCountStr(List<Payment> payments);
}
