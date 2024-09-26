package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.Transaction;
import com.example.fairhandborrowing.model.TransactionType;
import com.example.fairhandborrowing.model.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void depositMoney(BigDecimal amount, UserEntity initiator);
    void withdrawMoney(BigDecimal amount, UserEntity initiator);
    void sendMoneyUserToUser(UserEntity initiator, UserEntity recipient, BigDecimal amount, Loan loan, TransactionType transactionType);
    List<Transaction> getTransactionsByUser(UserEntity user);
}
