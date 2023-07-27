package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Model.Collateral;
import com.example.fairhandborrowing.Model.UserEntity;
import com.example.fairhandborrowing.Repository.CollateralRepository;
import com.example.fairhandborrowing.Repository.UserRepository;
import com.example.fairhandborrowing.Service.CollateralService;
import com.example.fairhandborrowing.mapper.CollateralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.fairhandborrowing.mapper.CollateralMapper.mapToModel;

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
