package com.system.management.repository;

import com.system.management.domain.entity.Crop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends CrudRepository<Crop,Integer> {
    List<Crop> findByUserUserId(Integer userId);
    Optional<Crop> findById(Integer cropId);
    void deleteByUserUserId(Integer userId);
    void deleteById(Integer cropId);
}
