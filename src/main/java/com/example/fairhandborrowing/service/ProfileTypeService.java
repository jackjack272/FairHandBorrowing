package com.example.fairhandborrowing.service;

import com.example.fairhandborrowing.model.ProfileType;

import java.util.List;

public interface ProfileTypeService {
    List<String> getProfileTypes();

    ProfileType getProfileByType(String type);
}
