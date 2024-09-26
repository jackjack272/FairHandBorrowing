package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileTypeRepository extends JpaRepository<ProfileType, Long> {
    ProfileType findProfileTypeByTypeName(String typeName);
}
