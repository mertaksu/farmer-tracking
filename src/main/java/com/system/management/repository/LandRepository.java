package com.system.management.repository;

import com.system.management.domain.entity.Land;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandRepository extends CrudRepository<Land,Integer> {
    List<Land> findByUserUserId(Integer userId);
    void deleteByUserUserId(Integer userId);
    void deleteById(Integer landId);
}
