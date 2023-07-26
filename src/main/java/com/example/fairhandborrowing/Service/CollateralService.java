package com.example.fairhandborrowing.Service;

import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CollateralService {
    List<CollateralDto> findAllCollaterals();

    CollateralDto findOneCollateral(long id);

    void deleteCollateral(long id);

    CollateralDto createCollateral(String userName, CollateralDto collateralDto);


}
