package com.system.management.repository;

import com.system.management.domain.entity.Crop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends CrudRepository<Crop,Integer> {
    List<Crop> findByUserUserId(Integer userId);
    void deleteByUserUserId(Integer userId);
}
