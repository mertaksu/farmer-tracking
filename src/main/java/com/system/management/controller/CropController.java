package com.system.management.controller;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.request.CropRequest;
import com.system.management.domain.response.CropResponse;
import com.system.management.service.ICropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CropController {

    private final ICropService cropService;

    @PostMapping(path = "/crop",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropResponse> saveNewCrop(HttpServletRequest request,@RequestBody CropRequest cropRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(cropService.addCrop(cropRequest,Integer.parseInt((String)request.getAttribute("userId"))));
    }

    @GetMapping(path = "/crop", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Crop>> getUsersCrop(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(cropService.getCropsOfUser(Integer.parseInt((String) request.getAttribute("userId"))));
    }

    @DeleteMapping(path = "/crop/{cropId}")
    public ResponseEntity<Boolean> deleteCrop(@PathVariable Integer cropId) {
        Boolean isDeleted = cropService.deleteCrop(cropId);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

    @PutMapping(path = "/crop/{cropId}/name/{cropName}")
    public ResponseEntity<Boolean> updateCropName(@PathVariable Integer cropId,@PathVariable String cropName) {
        Boolean isUpdated = cropService.updateCropName(cropId,cropName);
        return ResponseEntity.status(HttpStatus.OK).body(isUpdated);
    }
}
