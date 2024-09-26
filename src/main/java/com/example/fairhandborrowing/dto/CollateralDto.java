package com.example.fairhandborrowing.dto;

import com.example.fairhandborrowing.model.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CollateralDto {

    private Long id;
    private String brand;
    private Boolean inUse;


    @NotEmpty(message = "item name cant be empty")
    @NotNull
    private String itemName;

    @NotEmpty(message = "item condition cant be empty")
    @NotNull
    private String condition;

    @NotEmpty(message = "item description cant be empty")
    @NotNull
    private String description;

    @NotEmpty(message = "item value cant be empty")
    @NotNull
    private Double marketValue;

    private UserEntity user;
}

