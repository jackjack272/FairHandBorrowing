package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    private UserEntity initiator;

    @ManyToOne
    private UserEntity recipient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private TransactionType transactionType;

    @ManyToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private BigDecimal amount;

    @CreationTimestamp
    private Instant creationTimestamp;
}
