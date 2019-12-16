package com.system.management.service;

import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.UserResponse;

public interface IUserService {
    int saveUser(UserRequest userRequest);
    UserResponse getUser(Integer userId);
    boolean updateUser(Integer userId,UserRequest userRequest);
    void deleteUserById(Integer userId);
}
