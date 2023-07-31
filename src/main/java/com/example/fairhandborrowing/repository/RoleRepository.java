package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
