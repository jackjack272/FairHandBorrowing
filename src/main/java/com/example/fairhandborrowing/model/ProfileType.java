package com.example.fairhandborrowing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "profile_types")
public class ProfileType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String typeName;

    @OneToMany(mappedBy="profileType", cascade = CascadeType.MERGE)
    private List<UserEntity> user;

}
