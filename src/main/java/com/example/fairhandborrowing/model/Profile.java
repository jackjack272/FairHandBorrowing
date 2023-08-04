package com.example.fairhandborrowing.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue
    private long id;

    private String profile_img;
    private String bio;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
