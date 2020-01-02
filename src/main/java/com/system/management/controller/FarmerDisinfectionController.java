package com.system.management.controller;

import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.service.FarmerDisinfectionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FarmerDisinfectionController {

    private final FarmerDisinfectionService farmerDisinfectionService;

    @PostMapping(path = "/disinfection",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmerDisinfectionResponse>> saveFarmerDisinfections(@RequestBody FarmerDisinfectionRequest farmerDisinfectionRequest) {
        try {
            List<FarmerDisinfectionResponse> responseList = farmerDisinfectionService.saveNewFarmerDisinfection(farmerDisinfectionRequest);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/disinfection/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FarmerDisinfectionTransaction>> getFarmerDisinfections(@PathVariable Integer userId) {
        try {
            List<FarmerDisinfectionTransaction> responseList = farmerDisinfectionService.getFarmerDisinfectionTransactionsByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseList);
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "/disinfection/{disinfectionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteDisinfection(@PathVariable Integer disinfectionId) {
        try {
            farmerDisinfectionService.deleteDisinfectionTransactionById(disinfectionId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            log.error("Exception ",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
