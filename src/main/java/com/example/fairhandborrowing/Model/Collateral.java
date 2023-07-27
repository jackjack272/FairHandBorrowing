package com.example.fairhandborrowing.Model;


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
    @JoinColumn(name = "owned_by", nullable = false)
    private UserEntity ownedBy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "used_by", referencedColumnName = "id")
    private Loan Loan;
}
