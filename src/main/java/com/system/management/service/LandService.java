package com.system.management.service;

import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.LandRequest;
import com.system.management.domain.response.LandCoordinate;
import com.system.management.domain.response.LandResponse;
import com.system.management.repository.FarmerPlanRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LandService implements ILandService {

    private final UserRepository userRepository;

    private final LandRepository landRepository;

    private final FarmerPlanRepository farmerPlanRepository;

    @Override
    public LandResponse addLand(LandRequest landRequest,int userId) {
        LandResponse landResponse = new LandResponse();
        try {
            Land land = new Land();
            User user = userRepository.findByUserId(userId);
            land.setUser(user);
            land.setTitle(landRequest.getLandName());
            land.setLatitude(landRequest.getLatitude());
            land.setLongitude(landRequest.getLongitude());
            land.setPlaceName(landRequest.getPlaceName());

            Land savedLand = landRepository.save(land);

            landResponse.setId(savedLand.getId());
            landResponse.setLandName(savedLand.getTitle());
            landResponse.setUserId(savedLand.getUser().getUserId());
        } catch (Exception e) {
            log.error("Exception ",e);
        }
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
            log.error("Exception ",e);
            return false;
        }
    }

    @Override
    public List<Land> getLandsOfUser(Integer userId) {
        return landRepository.findByUserUserId(userId);
    }

    @Override
    public List<LandCoordinate> getLandsCoordOfUser(Integer userId) {
        List<Land> landList = landRepository.findByUserUserId(userId);
        List<LandCoordinate> landCoordinateList = new ArrayList<>();
        for (Land land:landList) {
            LandCoordinate landCoordinate = new LandCoordinate();
            landCoordinate.setTitle(land.getTitle());
            landCoordinate.setPlaceName(land.getPlaceName());
            landCoordinate.setLatitude(land.getLatitude());
            landCoordinate.setLongitude(land.getLongitude());
            landCoordinateList.add(landCoordinate);
        }
        return landCoordinateList;
    }

    @Override
    public boolean updateLandName(Integer landId, String landName) {
        try {
            Optional<Land> land = landRepository.findById(landId);
            if(land!=null && land.isPresent()) {
                land.get().setTitle(landName);
                landRepository.save(land.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Exception ",e);
            return false;
        }
    }
}
