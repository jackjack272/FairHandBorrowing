package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "transaction_types")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_type_id")
    private Long id;

    private String typeName;

    @OneToMany(mappedBy="transactionType", cascade = CascadeType.MERGE)
    private List<Transaction> transactions;
}
