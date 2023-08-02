package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "contract_id")
    private long contractId;

    @CreationTimestamp
    private LocalDateTime createdOn;

    private Timestamp borrowerSignedDate;
    private boolean borrowerSigned;

    private Timestamp lenderSignedDate;
    private boolean lenderSigned;

    @OneToOne(mappedBy = "contract")
    private LoanFunds loanFund;
}
