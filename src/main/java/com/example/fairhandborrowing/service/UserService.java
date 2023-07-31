package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.UserRegistrationDto;

import java.util.Optional;

public interface UserService {
    Optional<UserRegistrationDto> saveUser(UserRegistrationDto registrationDto);

    UserRegistrationDto findByEmail(String email) ;
    UserRegistrationDto findByUserName(String userName);

}
