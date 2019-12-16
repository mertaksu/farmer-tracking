package com.system.management.service;

import com.system.management.domain.entity.Land;

import java.util.List;

public interface ILandService {
    void addLand(Integer userId,String landName);
    void deleteLand(Integer landId);
    List<Land> getLandsOfUser(Integer userId);
}
