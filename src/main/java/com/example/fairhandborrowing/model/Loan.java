package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "loan_id")
    private Long id;

    private Double amountBorrowed;

    private Double interestRate;

    private int monthsToPay;

    private String description;

    private boolean isArchived;

    private boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private LoanStatus loanStatus;

    @CreationTimestamp(source = SourceType.DB)
    private Date createdOn;

    @UpdateTimestamp(source = SourceType.DB)
    private Date updatedOn;

    @OneToMany(mappedBy="loan", cascade = CascadeType.MERGE)
    private List<Collateral> collaterals;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.MERGE)
    private List<LoanFunds> loanFunds;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.MERGE)
    private List<Transaction> transactions;

}
