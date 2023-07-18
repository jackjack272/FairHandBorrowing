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
    public void saveUser(UserRegistrationDto registrationDto)
            throws  Exception {


        // validate the username is availabe
        if(this.findByUserName(registrationDto.getUserName()) !=null){
            throw new Exception("user name taken");
        } else if (this.findByEmail(registrationDto.getEmail()) !=null) {
            throw new Exception("email taken");
        } else if (registrationDto.getPassword().length() <2) {
            throw new Exception("password too short");

        } else{
            UserEntity user = new UserEntity();

            // how do i set the ID here?

//            user.setUserId(userRepository.find);

            user.setUserName(registrationDto.getUserName());
            user.setEmail(registrationDto.getEmail());
            user.setFirstName(registrationDto.getFirstName());
            user.setLastName(registrationDto.getLastName());
            user.setDob(registrationDto.getDob());

            Role role = roleRepository.findByName(USER_ROLE);

            user.setRoles(Arrays.asList(role));
            userRepository.save(user);
        }

    }

    @Override
    public UserRegistrationDto findByEmail(String email) {
        return mapToDto( userRepository.findByEmail(email));
    }

    @Override
    public UserRegistrationDto findByUserName(String userName) {
        return mapToDto(userRepository.findByUserName(userName));
    }


    @Override
    public Boolean validateEmailWithPassword(String email, String password) {
        if( userRepository.findByEmail(email).getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
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
                .userId(user.getUserId())
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
