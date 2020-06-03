package com.system.management.repository;

import com.system.management.domain.entity.FarmerPlanTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FarmerPlanRepository extends CrudRepository<FarmerPlanTransaction,Integer> {
    List<FarmerPlanTransaction> findByUserUserIdAndPlanDateLessThan(Integer userId, Date currenctDate);
    List<FarmerPlanTransaction> findByUserUserIdAndPlanDateGreaterThanEqualOrderByPlanDateAsc(Integer userId, Date currenctDate);
    void deleteById(Integer planId);
    void deleteByCropId(Integer cropId);
    void deleteByLandId(Integer landId);
    void deleteByUserUserId(Integer userId);
}
