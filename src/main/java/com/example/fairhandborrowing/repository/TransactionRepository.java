package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.mapper.UserMapper;
import com.example.fairhandborrowing.model.Transaction;
import com.example.fairhandborrowing.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByRecipient(UserEntity user);

    List<Transaction> findAllByInitiator(UserEntity user);
}
