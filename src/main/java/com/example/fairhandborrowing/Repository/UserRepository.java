package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);
    UserEntity findFirstByUserName(String userName);
}
