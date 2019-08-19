package com.system.management.service;

import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;

import java.util.List;

public interface IFarmerDisinfectionService {
    List<FarmerDisinfectionResponse> saveNewFarmerDisinfection(FarmerDisinfectionRequest farmerDisinfectionRequest);
    List<FarmerDisinfectionTransaction> getAllFarmerDisinfectionByUserId(Integer userId);
}
