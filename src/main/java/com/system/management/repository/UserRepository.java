package com.system.management.repository;

import com.system.management.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUserId(Integer userId);
    List<User> findByUserNameAndUserPass(String userName, String pass);
    User findByUserName(String userName);
    void deleteByUserId(Integer userId);
}
