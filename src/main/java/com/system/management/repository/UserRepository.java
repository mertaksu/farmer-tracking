package com.system.management.repository;

import com.system.management.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUserId(Integer userId);
    User findByUserNameAndUserPass(String userName,String pass);
    void deleteByUserId(Integer userId);
}
