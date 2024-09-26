package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "payment_id")
    private Long paymentId;

    private Timestamp dueDate;

    private Double amountDue;

    private Double interestDue;

    private Double totalDue;

    private Timestamp paymentDate;

    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
}
