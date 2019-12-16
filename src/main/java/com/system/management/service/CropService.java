package com.system.management.service;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.User;
import com.system.management.repository.CropRepository;
import com.system.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService implements ICropService {

    private final CropRepository cropRepository;

    private final UserRepository userRepository;

    @Override
    public void addCrop(Integer userId, String cropName) {
        Crop crop = new Crop();
        User user = userRepository.findByUserId(userId);
        crop.setUser(user);
        crop.setCropName(cropName);

        cropRepository.save(crop);
    }

    @Override
    public void deleteCrop(Integer cropId) {
        cropRepository.deleteById(cropId);
    }

    @Override
    public List<Crop> getCropsOfUser(Integer userId) {
        return cropRepository.findByUserUserId(userId);
    }
}
