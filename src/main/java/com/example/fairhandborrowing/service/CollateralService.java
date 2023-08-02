package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.model.Collateral;

import java.util.List;

public interface CollateralService {
    List<Collateral> findAllCollaterals();

    Collateral findOneCollateral(long id);

    void deleteCollateral(Long collateralId);

    Collateral createCollateral(String userName, CollateralDto collateralDto);

    List<Collateral> findAllCollateralsByUsername(String userName);

}
