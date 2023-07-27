package com.example.fairhandborrowing.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;

    private Double amountBorrowed;

    private Double interestRate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedEndDate;

    private Date createdOn;

    private UserRegistrationDto user;

    private List<CollateralDto> collaterals;
}
