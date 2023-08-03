package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "loan_funds")
public class LoanFunds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanFundId;

    private Double fundAmount;

    private boolean accepted;

    private boolean rejected;

    @ManyToOne
    private UserEntity borrower;

    @ManyToOne
    private UserEntity lender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;
}
