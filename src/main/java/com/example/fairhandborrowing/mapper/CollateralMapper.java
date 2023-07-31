package com.example.fairhandborrowing.mapper;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.model.Collateral;

public class CollateralMapper {
    public static Collateral mapToModel(CollateralDto collateralDto){
        Collateral.CollateralBuilder builder = Collateral.builder();
        builder.brand(collateralDto.getBrand())
                .itemName(collateralDto.getItemName())
                .itemCondition(collateralDto.getCondition())
                .description(collateralDto.getDescription())
                .marketValue(collateralDto.getMarketValue())
                .user(collateralDto.getUser());
        if(collateralDto.getId() != null) builder.id(collateralDto.getId());
        return builder.build();
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
                .user(collateral.getUser())
                .build();

        return collateralDto;
    }
}
