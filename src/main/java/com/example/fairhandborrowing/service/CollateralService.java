package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.CollateralDto;

import java.util.List;

public interface CollateralService {
    List<CollateralDto> findAllCollaterals();

    CollateralDto findOneCollateral(long id);

    void deleteCollateral(long id);

    CollateralDto createCollateral(String userName, CollateralDto collateralDto);

    List<CollateralDto> findAllCollateralsByUsername(String userName);

}
