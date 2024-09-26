package com.example.fairhandborrowing.service.implement;

import com.example.fairhandborrowing.model.ProfileType;
import com.example.fairhandborrowing.repository.ProfileTypeRepository;
import com.example.fairhandborrowing.service.ProfileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileTypeServiceImpl implements ProfileTypeService {
    @Autowired
    private ProfileTypeRepository profileTypeRepository;

    @Override
    public List<String> getProfileTypes() {
        List<ProfileType> profileTypeList = profileTypeRepository.findAll();

        return profileTypeList
                .stream()
                .map(profileType -> profileType.getTypeName())
                .collect(Collectors.toList());
    }

    @Override
    public ProfileType getProfileByType(String type) {
        return profileTypeRepository.findProfileTypeByTypeName(type);
    }

}
