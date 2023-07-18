package com.example.fairhandborrowing.Service;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.UserEntity;

public interface UserService {
    void saveUser(UserRegistrationDto registrationDto);

    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);


    // validate log in
    Boolean validateEmailWithPassword(String email, String password);
}
