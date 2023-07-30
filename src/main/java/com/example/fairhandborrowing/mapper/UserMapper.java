package com.example.fairhandborrowing.mapper;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.UserEntity;
import org.springframework.stereotype.Service;


public class UserMapper {
    public static UserEntity mapToModel(UserRegistrationDto user){
        UserEntity userEntity= UserEntity.builder()
                .username(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dob(user.getDob())
                .build();
        return userEntity;
    }

    public static UserRegistrationDto mapToDto(UserEntity user){

        UserRegistrationDto userDto= UserRegistrationDto.builder()
                .id(user.getUserId())
                .userName(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dob(user.getDob())
                .profileType(user.getProfileType().getTypeName())
                .build();

        return userDto;
    }
}
