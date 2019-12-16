package com.system.management.service;

import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LandService implements ILandService {

    private final UserRepository userRepository;

    private final LandRepository landRepository;

    @Override
    public void addLand(Integer userId,String landName) {
        Land land = new Land();
        User user = userRepository.findByUserId(userId);
        land.setUser(user);
        land.setLandName(landName);

        landRepository.save(land);
    }

    @Override
    public void deleteLand(Integer landId) {
        landRepository.deleteById(landId);
    }

    @Override
    public List<Land> getLandsOfUser(Integer userId) {
        return landRepository.findByUserUserId(userId);
    }
}
