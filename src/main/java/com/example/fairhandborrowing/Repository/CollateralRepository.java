package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollateralRepository extends JpaRepository<Collateral, Long> {
}
