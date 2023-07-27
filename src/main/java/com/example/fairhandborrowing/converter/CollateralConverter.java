package com.example.fairhandborrowing.converter;

import com.example.fairhandborrowing.DTO.CollateralDto;
import com.example.fairhandborrowing.Repository.CollateralRepository;
import com.example.fairhandborrowing.mapper.CollateralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CollateralConverter implements Converter<String, CollateralDto> {

    private final CollateralRepository collateralRepository;

    @Autowired
    public CollateralConverter(CollateralRepository collateralRepository) {
        this.collateralRepository = collateralRepository;
    }

    @Override
    public CollateralDto convert(String id) {
        return CollateralMapper.mapToDto(collateralRepository.findById(Long.valueOf(id)).get());
    }
}
