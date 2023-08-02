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
@Table(name = "loan_status")
public class LoanStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_status_id")
    private Long id;

    private String statusName;

    @OneToMany(mappedBy="loanStatus", cascade = CascadeType.MERGE)
    private List<Loan> loans;
}
