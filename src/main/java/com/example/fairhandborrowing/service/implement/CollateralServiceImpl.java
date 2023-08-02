package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.model.UserEntity;
import com.example.fairhandborrowing.repository.CollateralRepository;
import com.example.fairhandborrowing.repository.UserRepository;
import com.example.fairhandborrowing.service.CollateralService;
import com.example.fairhandborrowing.mapper.CollateralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollateralServiceImpl implements CollateralService {

    @Autowired
    private CollateralRepository collateralRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Collateral> findAllCollaterals() {
        List<Collateral> collaterals = collateralRepository.findAll();

        return collaterals;
    }

    @Override
    public List<Collateral> findAllCollateralsByUsername(String userName) {
        UserEntity user = userRepository.findFirstByUsername(userName);

        List<Collateral> collaterals = collateralRepository.findCollateralByUser(user);

        return collaterals;
    }

    @Override
    public Collateral findOneCollateral(long id) {
        return collateralRepository.findById(id).get();
    }

    @Override
    public void deleteCollateral(Long collateralId) {
        collateralRepository.deleteById(collateralId);
    }

    @Override
    public Collateral createCollateral(String userName, CollateralDto collateralDto) {
        UserEntity user = userRepository.findFirstByUsername(userName);
        Collateral collateral = CollateralMapper.mapToModel(collateralDto);
        collateral.setInUse(false);
        collateral.setUser(user);

        Collateral collateralResult = collateralRepository.save(collateral);
        return collateralResult;
    }


}
