package com.system.management.service;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.request.CropRequest;
import com.system.management.domain.response.CropResponse;

import java.util.List;

public interface ICropService {
    CropResponse addCrop(CropRequest cropRequest);
    boolean deleteCrop(Integer cropId);
    List<Crop> getCropsOfUser(Integer userId);
    boolean updateCropName(Integer cropId,String cropName);
}
