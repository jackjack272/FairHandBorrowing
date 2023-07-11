package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Model.CollateralModel;
import com.example.fairhandborrowing.Repository.CollateralRepo;
import com.example.fairhandborrowing.Service.CollateralService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollateralServiceImpl implements CollateralService {

    private CollateralRepo collateralRepo;

    public CollateralServiceImpl(CollateralRepo collateralRepo) {
        this.collateralRepo = collateralRepo;
    }

    @Override
    public List<CollateralDto> findAllCollateral() {
        List<CollateralModel> collateralModel= collateralRepo.findAll();

        return collateralModel.stream().map(
            oneItem->mapToDto(oneItem)
        ).collect(Collectors.toList());
    }

    @Override
    public CollateralDto findOneCollateral(long id) {
        return mapToDto(collateralRepo.findById(id).get());
    }

    @Override
    public void deleteCollateral(long id) {
        collateralRepo.deleteById(id);
    }

    @Override
    public void addNewCollateral(CollateralDto collateralDto) {
        collateralRepo.save(mapToModel(collateralDto));
    }

    private CollateralModel mapToModel(CollateralDto collateralDto){
        CollateralModel model = CollateralModel.builder()
            .id(collateralDto.getId())
            .brand(collateralDto.getBrand())
            .inUse(false)
            .itemName(collateralDto.getItemName())
            .condition(collateralDto.getCondition())
            .decription(collateralDto.getDecription())
            .marketValue(collateralDto.getMarketValue())

            .build();

        return model;
    }
    private CollateralDto mapToDto(CollateralModel collateralModel){
        CollateralDto collateralDto= CollateralDto.builder()
            .id(collateralModel.getId())
            .brand(collateralModel.getBrand())
            .inUse(collateralModel.getInUse())

            .itemName(collateralModel.getItemName())
            .Condition(collateralModel.getCondition())
            .decription(collateralModel.getDecription())
            .marketValue(collateralModel.getMarketValue())

        .build();

        return collateralDto;
    }
}
