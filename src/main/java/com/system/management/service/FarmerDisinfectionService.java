package com.system.management.service;

import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.repository.FarmerDisinfectionRepository;
import com.system.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class FarmerDisinfectionService implements IFarmerDisinfectionService{

    FarmerDisinfectionRepository farmerDisinfectionRepository;

    UserRepository userRepository;

    @Override
    public List<FarmerDisinfectionResponse> saveNewFarmerDisinfection(FarmerDisinfectionRequest farmerDisinfectionRequest) {
        try {
            List<FarmerDisinfectionTransaction> listOfFarmerTransactions = new ArrayList<>();
            List<FarmerDisinfectionResponse> responseList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            User user = userRepository.findByUserId(farmerDisinfectionRequest.getUserId());
            Date startDate = farmerDisinfectionRequest.getPharmacyStartDate();
            Date endDate = farmerDisinfectionRequest.getPharmacyFinishDate();
            calendar.setTime(startDate);
            long diff = endDate.getTime() - startDate.getTime();
            long totalDays = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

            for (int i=0;i<totalDays;i=i+Math.toIntExact(farmerDisinfectionRequest.getEveryFewDays())) {
                calendar.add(Calendar.DATE,farmerDisinfectionRequest.getEveryFewDays());
                FarmerDisinfectionTransaction farmerDisinfectionTransaction = new FarmerDisinfectionTransaction();
                farmerDisinfectionTransaction.setPharmacyType(farmerDisinfectionRequest.getPharmacyType());
                farmerDisinfectionTransaction.setLand(farmerDisinfectionRequest.getLand());
                farmerDisinfectionTransaction.setCrop(farmerDisinfectionRequest.getCrop());
                farmerDisinfectionTransaction.setPharmacyDate(calendar.getTime());
                farmerDisinfectionTransaction.setStatus(farmerDisinfectionRequest.getStatus());
                farmerDisinfectionTransaction.setUser(user);
                listOfFarmerTransactions.add(farmerDisinfectionTransaction);

                FarmerDisinfectionResponse farmerDisinfectionResponse = new FarmerDisinfectionResponse();
                farmerDisinfectionResponse.setPharmacyType(farmerDisinfectionRequest.getPharmacyType());
                farmerDisinfectionResponse.setLand(farmerDisinfectionRequest.getLand());
                farmerDisinfectionResponse.setCrop(farmerDisinfectionRequest.getCrop());
                farmerDisinfectionResponse.setPharmacyDate(calendar.getTime());
                farmerDisinfectionResponse.setStatus(farmerDisinfectionRequest.getStatus());
                responseList.add(farmerDisinfectionResponse);
            }

            farmerDisinfectionRepository.saveAll(listOfFarmerTransactions);
            log.info("Saved new disinfection for user: {}",user.getUserName());
            return responseList;
        } catch (Exception e) {
            log.error("Exception occured while disinfection record persisting to table.",e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<FarmerDisinfectionTransaction> getFarmerDisinfectionTransactionsByUserId(Integer userId) {
        return farmerDisinfectionRepository.findByUserUserId(userId);
    }

    @Transactional
    @Override
    public void deleteDisinfectionTransactionById(Integer id) {
        farmerDisinfectionRepository.deleteById(id);
    }
}
