package com.example.fairhandborrowing.mapper;

import com.example.fairhandborrowing.DTO.MoveMoneyDto;
import com.example.fairhandborrowing.Model.MoveMoney;
import org.springframework.stereotype.Service;


@Service
public class MoveMoneyMapper {

    //map to entity
    public MoveMoney mapToEntity(MoveMoneyDto money){
        MoveMoney money1= MoveMoney.builder()
                .id(money.getId())
                .amount(money.getAmount())
                .destination(money.getDestination())
                .withdrawn(money.getWithdrawn())
                .user(money.getUser())
                .build();
        return money1;
    }

    //map to DTO
    public MoveMoneyDto mapToDto(MoveMoney money){
        MoveMoneyDto dto= MoveMoneyDto.builder()
                .id(money.getId())
                .amount(money.getAmount())
                .destination(money.getDestination())
                .withdrawn(money.getWithdrawn())
                .user(money.getUser())
                .build();
        return dto;
    }

}
