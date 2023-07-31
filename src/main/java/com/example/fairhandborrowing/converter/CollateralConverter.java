package com.example.fairhandborrowing.converter;

import com.example.fairhandborrowing.dto.CollateralDto;
import com.example.fairhandborrowing.model.Collateral;
import com.example.fairhandborrowing.repository.CollateralRepository;
import com.example.fairhandborrowing.mapper.CollateralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CollateralConverter implements Converter<String, Collateral> {

    private final CollateralRepository collateralRepository;

    @Autowired
    public CollateralConverter(CollateralRepository collateralRepository) {
        this.collateralRepository = collateralRepository;
    }

    @Override
    public Collateral convert(String id) {
        return collateralRepository.findById(Long.valueOf(id)).get();
    }
}
