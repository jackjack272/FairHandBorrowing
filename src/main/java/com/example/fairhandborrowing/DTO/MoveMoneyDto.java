package com.example.fairhandborrowing.DTO;

import com.example.fairhandborrowing.Model.UserEntity;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class MoveMoneyDto {
        private Long id;
        private String destination;
        private Double amount;
        private Boolean withdrawn;

        private UserEntity user;
}
