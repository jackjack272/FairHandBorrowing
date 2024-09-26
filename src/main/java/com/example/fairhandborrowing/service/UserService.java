package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.model.ProfileType;
import com.example.fairhandborrowing.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserRegistrationDto> saveUser(UserRegistrationDto registrationDto);

    UserRegistrationDto findByEmail(String email) ;
    UserEntity findByUserName(String userName);

    List<UserEntity> findAllByType(ProfileType profileType);

    void updateUser(UserEntity userEntity);

}
