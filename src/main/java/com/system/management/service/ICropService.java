package com.system.management.service;

import com.system.management.domain.entity.Crop;

import java.util.List;

public interface ICropService {
    void addCrop(Integer userId,String cropName);
    void deleteCrop(Integer cropId);
    List<Crop> getCropsOfUser(Integer userId);
}
