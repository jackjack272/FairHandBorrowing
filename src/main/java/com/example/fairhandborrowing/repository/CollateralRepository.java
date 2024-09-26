package com.example.fairhandborrowing.repository;

import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollateralRepository extends JpaRepository<Collateral, Long> {

    List<Collateral> findCollateralByUser(UserEntity user);
}
