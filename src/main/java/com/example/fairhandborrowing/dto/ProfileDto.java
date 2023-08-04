package com.example.fairhandborrowing.dto;

import com.example.fairhandborrowing.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Builder
public class ProfileDto {
    private long id;
    private String profile_img;
    private String bio;
    private UserEntity user;

}
