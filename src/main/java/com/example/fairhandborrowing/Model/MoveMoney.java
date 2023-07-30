package com.example.fairhandborrowing.Model;


import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Builder
@Entity
@Table(name = "money")
public class MoveMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String destination;
    private Double amount;
    private Boolean withdrawn;

//     user id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id_fk")//referencedColumnName = "userId") // this is the game it gets in the db.
    private  UserEntity user;


}
