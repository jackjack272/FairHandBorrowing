package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
