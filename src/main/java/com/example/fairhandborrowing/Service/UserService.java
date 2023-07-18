package com.example.fairhandborrowing.Service;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.UserEntity;

public interface UserService {
    void saveUser(UserRegistrationDto registrationDto)throws  Exception ;

    UserRegistrationDto findByEmail(String email) ;
    UserRegistrationDto findByUserName(String userName);


    // validate log in
    Boolean validateEmailWithPassword(String email, String password);
}
