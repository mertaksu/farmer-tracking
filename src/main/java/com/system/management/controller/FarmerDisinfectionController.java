package com.system.management.controller;

import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.service.FarmerDisinfectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class FarmerDisinfectionController {

    FarmerDisinfectionService farmerDisinfectionService;

    @PostMapping(path = "/disinfection",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmerDisinfectionResponse>> saveFarmerDisinfections(@RequestBody FarmerDisinfectionRequest farmerDisinfectionRequest) {
        List<FarmerDisinfectionResponse> responseList = farmerDisinfectionService.saveNewFarmerDisinfection(farmerDisinfectionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
    
    @GetMapping(path = "/disinfection/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmerDisinfectionTransaction>> getAllFarmerDisinfectionsByUserId(@PathVariable("userId") Integer userId) {
        List<FarmerDisinfectionTransaction> responseList = farmerDisinfectionService.getAllFarmerDisinfectionByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
    
}
