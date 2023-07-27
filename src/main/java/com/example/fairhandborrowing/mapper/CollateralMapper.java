package com.example.fairhandborrowing.mapper;

import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Model.Collateral;

public class CollateralMapper {
    public static Collateral mapToModel(CollateralDto collateralDto){
        Collateral model = Collateral.builder()
                .id(collateralDto.getId())
                .brand(collateralDto.getBrand())
                .itemName(collateralDto.getItemName())
                .itemCondition(collateralDto.getCondition())
                .description(collateralDto.getDescription())
                .marketValue(collateralDto.getMarketValue())
                .build();

        return model;
    }
    public static CollateralDto mapToDto(Collateral collateral){
        CollateralDto collateralDto= CollateralDto.builder()
                .id(collateral.getId())
                .brand(collateral.getBrand())
                .inUse(collateral.getInUse())
                .itemName(collateral.getItemName())
                .condition(collateral.getItemCondition())
                .description(collateral.getDescription())
                .marketValue(collateral.getMarketValue())
                .build();

        return collateralDto;
    }
}
