package com.system.management.service;

import com.system.management.domain.entity.Land;
import com.system.management.domain.request.LandRequest;
import com.system.management.domain.response.LandResponse;

import java.util.List;

public interface ILandService {
    LandResponse addLand(LandRequest landRequest);
    boolean deleteLand(Integer landId);
    List<Land> getLandsOfUser(Integer userId);
    boolean updateLandName(Integer landId,String landName);
}
