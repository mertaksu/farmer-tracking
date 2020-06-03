package com.system.management.controller;

import com.system.management.domain.entity.FarmerPlanTransaction;
import com.system.management.domain.request.FarmerPlanRequest;
import com.system.management.domain.response.FarmerPlanResponse;
import com.system.management.service.IFarmerPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FarmerPlanController {

    private final IFarmerPlanService farmerPlanService;

    @PostMapping(path = "/plan",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> saveFarmerPlan(HttpServletRequest request,@RequestBody FarmerPlanRequest farmerPlanRequest) {
        try {
            Boolean response = farmerPlanService.saveNewFarmerPlan(farmerPlanRequest,Integer.parseInt((String) request.getAttribute("userId")));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/plan", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmerPlanResponse>> getFarmerPlans(HttpServletRequest request) {
        try {
            List<FarmerPlanTransaction> responseList = farmerPlanService.getFarmerPlanTransactionsByUserIdAfterCurrDate(Integer.valueOf((String) request.getAttribute("userId")));
            return ResponseEntity.status(HttpStatus.OK).body(getFarmerPlanList(responseList));
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/prevPlan", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmerPlanResponse>> getPrevFarmerPlans(HttpServletRequest request) {
        try {
            List<FarmerPlanTransaction> responseList = farmerPlanService.getFarmerPlanTransactionsByUserIdBeforeCurrDate(Integer.valueOf((String) request.getAttribute("userId")));
            return ResponseEntity.status(HttpStatus.OK).body(getFarmerPlanList(responseList));
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private List<FarmerPlanResponse> getFarmerPlanList(List<FarmerPlanTransaction> responseList ) {
        List<FarmerPlanResponse> farmerPlanResponseList = new ArrayList<>();
        for (FarmerPlanTransaction farmerPlanTransaction:responseList) {
            FarmerPlanResponse farmerPlanResponse = new FarmerPlanResponse();
            farmerPlanResponse.setFarmerPlanId(farmerPlanTransaction.getId());
            farmerPlanResponse.setCropName(farmerPlanTransaction.getCrop().getCropName());
            farmerPlanResponse.setLandName(farmerPlanTransaction.getLand().getLandName());
            farmerPlanResponse.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").format(farmerPlanTransaction.getPlanDate()));
            farmerPlanResponse.setPlanType(farmerPlanTransaction.getPlanType());
            farmerPlanResponseList.add(farmerPlanResponse);
        }
        return farmerPlanResponseList;
    }

    @DeleteMapping(path = "/plan/{planId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteFarmerPlan(@PathVariable Integer planId) {
        try {
            farmerPlanService.deletePlanTransactionById(planId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
