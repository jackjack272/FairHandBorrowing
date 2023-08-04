package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dob;

    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal availableFunds = BigDecimal.ZERO;

    @Column(columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal FundsOnHold = BigDecimal.ZERO;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private ProfileType profileType;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
}
