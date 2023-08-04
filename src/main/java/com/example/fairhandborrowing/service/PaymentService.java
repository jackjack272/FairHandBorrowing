package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.model.Loan;

public interface PaymentService {
    void generatePaymentSchedule(Loan loan);

    void processPayment(Long paymentId);

    String getPaidCountStr();
}
