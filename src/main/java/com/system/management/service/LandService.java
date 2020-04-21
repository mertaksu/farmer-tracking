package com.system.management.service;

import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.LandRequest;
import com.system.management.domain.response.LandResponse;
import com.system.management.repository.FarmerPlanRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LandService implements ILandService {

    private final UserRepository userRepository;

    private final LandRepository landRepository;

    private final FarmerPlanRepository farmerPlanRepository;

    @Override
    public LandResponse addLand(LandRequest landRequest,int userId) {
        Land land = new Land();
        User user = userRepository.findByUserId(userId);
        land.setUser(user);
        land.setLandName(landRequest.getLandName());
        land.setLatitude(landRequest.getLatitude());
        land.setLongitude(landRequest.getLongitude());

        Land savedLand = landRepository.save(land);
        LandResponse landResponse = new LandResponse();
        landResponse.setId(savedLand.getId());
        landResponse.setLandName(savedLand.getLandName());
        landResponse.setUserId(savedLand.getUser().getUserId());
        return landResponse;
    }

    @Transactional
    @Override
    public boolean deleteLand(Integer landId) {
        try {
            farmerPlanRepository.deleteByLandId(landId);
            landRepository.deleteById(landId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Land> getLandsOfUser(Integer userId) {
        return landRepository.findByUserUserId(userId);
    }

    @Override
    public boolean updateLandName(Integer landId, String landName) {
        try {
            Optional<Land> land = landRepository.findById(landId);
            if(land!=null && land.isPresent()) {
                land.get().setLandName(landName);
                landRepository.save(land.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
