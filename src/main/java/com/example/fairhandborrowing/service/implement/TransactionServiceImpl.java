package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.model.Loan;
import com.example.fairhandborrowing.model.Transaction;
import com.example.fairhandborrowing.model.TransactionType;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.model.constants.Constants;
import com.example.fairhandborrowing.repository.TransactionRepository;
import com.example.fairhandborrowing.repository.TransactionTypeRepository;
import com.example.fairhandborrowing.service.TransactionService;
import com.example.fairhandborrowing.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void depositMoney(BigDecimal amount, UserEntity initiator) {
        Transaction.TransactionBuilder transactionBuilder = Transaction.builder();
        transactionBuilder.transactionType(transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_DEPOSIT));
        transactionBuilder.initiator(initiator);
        transactionBuilder.amount(amount);

        transactionRepository.save(transactionBuilder.build());

        initiator.setAvailableFunds(initiator.getAvailableFunds().add(amount));
        userService.updateUser(initiator);
    }

    @Override
    public void withdrawMoney(BigDecimal amount, UserEntity initiator) {
        Transaction.TransactionBuilder transactionBuilder = Transaction.builder();
        transactionBuilder.transactionType(transactionTypeRepository.findTransactionTypeByTypeName(Constants.TRANSACTION_WITHDRAW));
        transactionBuilder.initiator(initiator);
        transactionBuilder.amount(amount);

        transactionRepository.save(transactionBuilder.build());

        initiator.setAvailableFunds(initiator.getAvailableFunds().subtract(amount));
        userService.updateUser(initiator);
    }

    @Override
    public List<Transaction> getTransactionsByUser(UserEntity user) {
        List<Transaction> result = new ArrayList<>();
        List<Transaction> transactionsInitiated = transactionRepository.findAllByInitiator(user);
        List<Transaction> transactionsRecipients = transactionRepository.findAllByRecipient(user);

        if(!transactionsInitiated.isEmpty())
            result.addAll(transactionsInitiated);

        if(!transactionsRecipients.isEmpty())
            result.addAll(transactionsRecipients);

        return result;
    }

    @Override
    public void sendMoneyUserToUser(UserEntity initiator, UserEntity recipient, BigDecimal amount, Loan loan, TransactionType transactionType) {
        Transaction.TransactionBuilder transactionBuilder = Transaction.builder();
        transactionBuilder.transactionType(transactionType);
        transactionBuilder.initiator(initiator);
        transactionBuilder.recipient(recipient);
        transactionBuilder.amount(amount);
        transactionBuilder.loan(loan);
        transactionRepository.save(transactionBuilder.build());
    }



}
