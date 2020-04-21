package com.system.management.service;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.FarmerPlanTransaction;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.FarmerPlanRequest;
import com.system.management.repository.CropRepository;
import com.system.management.repository.FarmerPlanRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class FarmerPlanService implements IFarmerPlanService {

    FarmerPlanRepository farmerPlanRepository;

    UserRepository userRepository;

    LandRepository landRepository;

    CropRepository cropRepository;

    @Override
    public Boolean saveNewFarmerPlan(FarmerPlanRequest farmerPlanRequest,int userId) {
        try {
            List<FarmerPlanTransaction> farmerPlanTransactionList = new ArrayList<>();

            User user = userRepository.findByUserId(userId);
            Optional<Land> land = landRepository.findById(Integer.valueOf(farmerPlanRequest.getLandId()));
            Optional<Crop> crop = cropRepository.findById(Integer.valueOf(farmerPlanRequest.getCropId()));
            String[] dateArr = farmerPlanRequest.getPlanDate();
            for (String date:dateArr) {
                FarmerPlanTransaction farmerPlanTransaction = new FarmerPlanTransaction();
                farmerPlanTransaction.setUser(user);
                crop.ifPresent(farmerPlanTransaction::setCrop);
                land.ifPresent(farmerPlanTransaction::setLand);
                farmerPlanTransaction.setPlanType(farmerPlanRequest.getPlanType());
                farmerPlanTransaction.setStatus(false);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                farmerPlanTransaction.setPlanDate(simpleDateFormat.parse(date));
                farmerPlanTransactionList.add(farmerPlanTransaction);
            }
            farmerPlanRepository.saveAll(farmerPlanTransactionList);
            log.info("Saved new plan for user: {}",user.getUserName());
            return true;
        } catch (Exception e) {
            log.error("Exception occured while plan record persisting to table.",e);
            return false;
        }
    }

    @Override
    public List<FarmerPlanTransaction> getFarmerPlanTransactionsByUserId(Integer userId) {
        return farmerPlanRepository.findByUserUserIdOrderByPlanDateAsc(userId);
    }

    @Transactional
    @Override
    public void deletePlanTransactionById(Integer id) {
        farmerPlanRepository.deleteById(id);
    }
}
