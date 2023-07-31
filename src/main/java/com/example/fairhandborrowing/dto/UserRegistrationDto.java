package com.example.fairhandborrowing.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegistrationDto {

    private Long id;

    @NotEmpty
    private String userName;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @NotEmpty
    private String profileType ;
}
