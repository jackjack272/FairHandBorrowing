package com.example.fairhandborrowing.Service.implement;

import com.example.fairhandborrowing.Model.ProfileType;
import com.example.fairhandborrowing.Repository.ProfileTypeRepository;
import com.example.fairhandborrowing.Service.ProfileTypeService;
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

}
