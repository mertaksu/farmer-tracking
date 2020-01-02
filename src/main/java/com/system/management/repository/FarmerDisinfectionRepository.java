package com.system.management.repository;

import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface FarmerDisinfectionRepository extends CrudRepository<FarmerDisinfectionTransaction,Integer> {
    List<FarmerDisinfectionTransaction> findByUserUserId(Integer userId);
    void deleteById(Integer disinfectionId);
    void deleteByCropId(Integer cropId);
    void deleteByLandId(Integer landId);
    void deleteByUserUserId(Integer userId);
}
