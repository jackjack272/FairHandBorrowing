package com.example.fairhandborrowing.Service;

import com.example.fairhandborrowing.DTO.CollateralDto;

import java.util.List;

public interface CollateralService {
    List<CollateralDto> findAllCollateral();

    CollateralDto findOneCollateral(long id);

    void deleteCollateral(long id);

    void addNewCollateral(CollateralDto collateralDto); // assume new item is not in use: mapToMode = .inUse(false)



}
