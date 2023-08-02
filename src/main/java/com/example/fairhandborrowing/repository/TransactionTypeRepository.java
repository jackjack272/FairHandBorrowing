package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findTransactionTypeByTypeName(String name);
}
