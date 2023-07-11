package com.example.fairhandborrowing.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Data
@Table(name = "collateral")
public class CollateralModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String itemName;
    private String brand;

    @Column(name = "item_condition")
    private String condition;
    private String decription;
    private Double marketValue;

    private Boolean inUse;
}
