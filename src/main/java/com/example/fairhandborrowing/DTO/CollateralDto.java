package com.example.fairhandborrowing.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private String Condition;

    @NotEmpty(message = "item description cant be empty")
    @NotNull
    private String decription;

    @NotEmpty(message = "item value cant be empty")
    @NotNull
    private Double marketValue;

}
