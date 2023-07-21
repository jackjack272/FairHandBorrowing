package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String userName);
    UserEntity findFirstByUsername(String userName);
}
