package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.Role;
import com.example.fairhandborrowing.Model.UserEntity;
import com.example.fairhandborrowing.Repository.RoleRepository;
import com.example.fairhandborrowing.Repository.UserRepository;
import com.example.fairhandborrowing.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static String USER_ROLE = "USER";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserRegistrationDto> saveUser(UserRegistrationDto registrationDto) {

        UserEntity user = new UserEntity();
        user.setUserName(registrationDto.getUserName());
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setDob(registrationDto.getDob());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName(USER_ROLE);

        user.setRoles(Arrays.asList(role));

        UserEntity savedUser = userRepository.save(user);
        Optional<UserRegistrationDto> dto = Optional.of(mapToDto(savedUser));
        return dto;

    }

    @Override
    public UserRegistrationDto findByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent() ? mapToDto(user.get()) : null;
    }

    @Override
    public UserRegistrationDto findByUserName(String userName) {
        Optional<UserEntity> user = userRepository.findByUserName(userName);

        return user.isPresent() ? mapToDto(user.get()) : null;
    }

    private UserEntity mapToModel(UserRegistrationDto user){
        //when regestering the user wont have an id until its in the db
        UserEntity userEntity= UserEntity.builder()
//                .userId(user.getUserId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .dob(user.getDob())
                .build();
        return userEntity;
    }

    private UserRegistrationDto mapToDto(UserEntity user){

        UserRegistrationDto userDto= UserRegistrationDto.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .dob(user.getDob())
                .build();

        return userDto;
    }

}
