package com.example.fairhandborrowing.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
@Table(name = "collaterals")
public class Collateral {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "collateral_id")
    private long id;
    private String itemName;
    private String brand;
    private String itemCondition;
    private String description;
    private Double marketValue;
    private Boolean inUse;
    private String imageUrl;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id")
    private Loan loan;
}
