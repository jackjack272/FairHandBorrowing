package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.DTO.UserRegistrationDto;
import com.example.fairhandborrowing.Model.Role;
import com.example.fairhandborrowing.Model.UserEntity;
import com.example.fairhandborrowing.Repository.RoleRepository;
import com.example.fairhandborrowing.Repository.UserRepository;
import com.example.fairhandborrowing.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private final static String USER_ROLE = "USER";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveUser(UserRegistrationDto registrationDto) {
        UserEntity user = new UserEntity();
        user.setUserName(registrationDto.getUserName());
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setDob(registrationDto.getDob());

        Role role = roleRepository.findByName(USER_ROLE);
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean validateEmailWithPassword(String email, String password) {
        if( userRepository.findByEmail(email).getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }
}
