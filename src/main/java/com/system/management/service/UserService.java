package com.system.management.service;

import com.system.management.authentication.TokenAuthentication;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.UserResponse;
import com.system.management.repository.CropRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements IUserService{

    UserRepository userRepository;

    LandRepository landRepository;

    CropRepository cropRepository;

    PasswordEncoder passEncoder;

    public int saveUser(UserRequest userRequest) {
        try {
            User user = new User();
            user.setUserName(userRequest.getUserName());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserGsm(userRequest.getUserGsm());
            user.setUserPass(passEncoder.encode(userRequest.getUserPass()));
            User savedUser = userRepository.save(user);

            for (Land land:userRequest.getLandList()) {
                land.setUser(savedUser);
                landRepository.save(land);
            }

            for (Crop crop:userRequest.getCropList()) {
                crop.setUser(savedUser);
                cropRepository.save(crop);
            }
            log.info("New User Saved. UserName: {}",savedUser.getUserName());
            return savedUser.getUserId();
        } catch (Exception e) {
            log.error("Exception occured while saving new user.",e);
            return 0;
        }
    }

    public UserResponse getUser(Integer userId) {
        try {
            UserResponse userResponse = new UserResponse();
            User user = userRepository.findByUserId(userId);
            List<Land> landList = landRepository.findByUserUserId(userId);
            List<Crop> cropList = cropRepository.findByUserUserId(userId);
            userResponse.setUserName(user.getUserName());
            userResponse.setUserEmail(user.getUserEmail());
            userResponse.setUserGsm(user.getUserGsm());
            userResponse.setCropList(cropList);
            userResponse.setLandList(landList);
            return userResponse;
        } catch (Exception e) {
            log.error("Exception occured while getting user.",e);
            return null;
        }
    }

    public Boolean updateUser(Integer userId,UserRequest userRequest) {
        try {
            User user = userRepository.findByUserId(userId);
            user.setUserGsm(userRequest.getUserGsm());
            user.setUserEmail(userRequest.getUserEmail());

            landRepository.deleteByUserUserId(userId);
            cropRepository.deleteByUserUserId(userId);

            for (Land land:userRequest.getLandList()) {
                land.setUser(user);
                landRepository.save(land);
            }

            for (Crop crop:userRequest.getCropList()) {
                crop.setUser(user);
                cropRepository.save(crop);
            }

            userRepository.save(user);
            return true;
        }catch (Exception e) {
            log.error("Exception occured while getting user.",e);
            return false;
        }
    }

    public String getUserId(String userName) {
        User user = userRepository.findByUserName(userName);
        if(user!=null)
            return String.valueOf(user.getUserId());
        else return null;
    }
}
