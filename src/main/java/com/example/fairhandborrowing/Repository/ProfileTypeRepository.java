package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileTypeRepository extends JpaRepository<ProfileType, Long> {
    ProfileType findProfileTypeByTypeName(String typeName);
}
