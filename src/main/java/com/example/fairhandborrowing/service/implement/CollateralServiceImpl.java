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
    public List<CollateralDto> findAllCollaterals() {
        List<Collateral> collateral = collateralRepository.findAll();

        return collateral.stream().map(
            oneItem-> {
                CollateralDto dto = CollateralMapper.mapToDto(oneItem);
                return dto;
            }
        ).collect(Collectors.toList());
    }

    @Override
    public List<CollateralDto> findAllCollateralsByUsername(String userName) {
        UserEntity user = userRepository.findFirstByUsername(userName);

        List<Collateral> collaterals = collateralRepository.findCollateralByOwnedBy(user);

        return collaterals.stream().map(collateral -> CollateralMapper.mapToDto(collateral)).collect(Collectors.toList());
    }

    @Override
    public CollateralDto findOneCollateral(long id) {
        return CollateralMapper.mapToDto(collateralRepository.findById(id).get());
    }

    @Override
    public void deleteCollateral(long id) {
        collateralRepository.deleteById(id);
    }

    @Override
    public CollateralDto  createCollateral(String userName, CollateralDto collateralDto) {
        UserEntity user = userRepository.findFirstByUsername(userName);
        Collateral collateral = CollateralMapper.mapToModel(collateralDto);
        collateral.setInUse(false);
        collateral.setOwnedBy(user);

        Collateral collateralResult = collateralRepository.save(collateral);
        return CollateralMapper.mapToDto(collateralResult);
    }


}
