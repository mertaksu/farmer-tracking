package com.system.management.service;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.CropRequest;
import com.system.management.domain.response.CropResponse;
import com.system.management.repository.CropRepository;
import com.system.management.repository.FarmerDisinfectionRepository;
import com.system.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CropService implements ICropService {

    private final CropRepository cropRepository;

    private final UserRepository userRepository;

    private final FarmerDisinfectionRepository farmerDisinfectionRepository;

    @Override
    public CropResponse addCrop(CropRequest cropRequest) {
        Crop crop = new Crop();
        User user = userRepository.findByUserId(cropRequest.getUserId());
        crop.setUser(user);
        crop.setCropName(cropRequest.getCropName());

        Crop savedCrop = cropRepository.save(crop);
        CropResponse cropResponse = new CropResponse();
        cropResponse.setId(savedCrop.getId());
        cropResponse.setCropName(savedCrop.getCropName());
        cropResponse.setUserId(savedCrop.getUser().getUserId());
        return cropResponse;
    }

    @Transactional
    @Override
    public boolean deleteCrop(Integer cropId) {
        try {
            cropRepository.deleteById(cropId);
            farmerDisinfectionRepository.deleteByCropId(cropId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Crop> getCropsOfUser(Integer userId) {
        return cropRepository.findByUserUserId(userId);
    }

    @Override
    public boolean updateCropName(Integer cropId,String cropName) {
        try {
            Optional<Crop> crop = cropRepository.findById(cropId);
            if(crop!=null && crop.isPresent()) {
                crop.get().setCropName(cropName);
                cropRepository.save(crop.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
