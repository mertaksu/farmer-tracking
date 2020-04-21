package com.system.management.service;

import com.system.management.domain.entity.FarmerPlanTransaction;
import com.system.management.domain.request.FarmerPlanRequest;

import java.util.List;

public interface IFarmerPlanService {
    Boolean saveNewFarmerPlan(FarmerPlanRequest farmerPlanRequest,int userId);
    List<FarmerPlanTransaction> getFarmerPlanTransactionsByUserId(Integer userId);
    void deletePlanTransactionById(Integer id);
}
