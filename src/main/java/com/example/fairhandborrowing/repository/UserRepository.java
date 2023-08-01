package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.ProfileType;
import com.example.fairhandborrowing.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String userName);
    UserEntity findFirstByUsername(String userName);

    List<UserEntity> findAllByProfileType(ProfileType profileType);
}
