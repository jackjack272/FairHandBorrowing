package com.example.fairhandborrowing.DTO;

import com.example.fairhandborrowing.Model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;

    private Double amountBorrowed;

    private Double interestRate;

    private LocalDateTime expectedEndDate;

    private LocalDateTime createdOn;

    private UserEntity user;
}
