package com.example.fairhandborrowing.Service;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserRegistrationDto> saveUser(UserRegistrationDto registrationDto);

    void updateUser(UserRegistrationDto registrationDto);
    UserRegistrationDto findByEmail(String email) ;
    UserRegistrationDto findByUserName(String userName);

}
