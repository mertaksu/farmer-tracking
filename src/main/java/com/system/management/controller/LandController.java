package com.system.management.controller;

import com.system.management.domain.entity.Land;
import com.system.management.domain.request.LandRequest;
import com.system.management.domain.response.LandResponse;
import com.system.management.service.ILandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LandController {
    private final ILandService landService;

    @PostMapping(path = "/land",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LandResponse> saveNewLand(@RequestBody LandRequest landRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(landService.addLand(landRequest));
    }

    @GetMapping(path = "/land/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Land>> getUsersLand(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(landService.getLandsOfUser(userId));
    }

    @DeleteMapping(path = "/land/{landId}")
    public ResponseEntity<Boolean> deleteLand(@PathVariable Integer landId) {
        Boolean isDeleted = landService.deleteLand(landId);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

    @PutMapping(path = "/land/{landId}/name/{landName}")
    public ResponseEntity<Boolean> updateLandName(@PathVariable Integer landId,@PathVariable String landName) {
        Boolean isUpdated = landService.updateLandName(landId,landName);
        return ResponseEntity.status(HttpStatus.OK).body(isUpdated);
    }
}
