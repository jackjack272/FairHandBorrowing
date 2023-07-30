package com.example.fairhandborrowing.Repository;

import com.example.fairhandborrowing.Model.MoveMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoveMoneyRepository extends JpaRepository<MoveMoney, Long> {

    List<MoveMoney> findMoveMoneyByUserUserId(MoveMoney moveMoney);


}
