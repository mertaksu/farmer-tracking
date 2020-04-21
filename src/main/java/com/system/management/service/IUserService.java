package com.system.management.service;

import com.system.management.domain.request.RegisterRequest;
import com.system.management.domain.response.UserResponse;

public interface IUserService {
    int saveUser(RegisterRequest registerRequest);
    UserResponse getUser(Integer userId);
    boolean updateUser(Integer userId, RegisterRequest registerRequest);
    void deleteUserById(Integer userId);
}
