package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.UserRegistrationDto;
import com.example.fairhandborrowing.model.ProfileType;
import com.example.fairhandborrowing.model.Role;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.ProfileTypeRepository;
import com.example.fairhandborrowing.repository.RoleRepository;
import com.example.fairhandborrowing.repository.UserRepository;
import com.example.fairhandborrowing.service.UserService;
import com.example.fairhandborrowing.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final static String USER_ROLE = "USER";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileTypeRepository profileTypeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserRegistrationDto> saveUser(UserRegistrationDto registrationDto) {

        UserEntity user = UserMapper.mapToModel(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setProfileType(profileTypeRepository.findProfileTypeByTypeName(registrationDto.getProfileType()));
        user.setAvailableFunds(BigDecimal.ZERO);
        user.setFundsOnHold(BigDecimal.ZERO);

        Role role = roleRepository.findByName(USER_ROLE);

        user.setRoles(Arrays.asList(role));

        UserEntity savedUser = userRepository.save(user);
        Optional<UserRegistrationDto> dto = Optional.of(UserMapper.mapToDto(savedUser));
        return dto;

    }

    @Override
    public UserRegistrationDto findByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent() ? UserMapper.mapToDto(user.get()) : null;
    }

    @Override
    public UserEntity findByUserName(String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);

        return user.isPresent() ? user.get() : null;
    }


    @Override
    public List<UserEntity> findAllByType(ProfileType type) {
        return userRepository.findAllByProfileType(type);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
