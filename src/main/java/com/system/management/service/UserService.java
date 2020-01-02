package com.system.management.service;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.UserResponse;
import com.system.management.repository.CropRepository;
import com.system.management.repository.FarmerDisinfectionRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements IUserService{

    UserRepository userRepository;

    LandRepository landRepository;

    CropRepository cropRepository;

    FarmerDisinfectionRepository farmerDisinfectionRepository;

    public int saveUser(UserRequest userRequest) {
        try {
            User user = new User();
            user.setUserName(userRequest.getUserName());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserGsm(userRequest.getUserGsm());
            user.setUserPass(userRequest.getUserPass());
            user.setCropList(userRequest.getCropList());
            user.setLandList(userRequest.getLandList());
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
            if(user!=null) {
                List<Land> landList = landRepository.findByUserUserId(userId);
                List<Crop> cropList = cropRepository.findByUserUserId(userId);
                userResponse.setUserName(user.getUserName());
                userResponse.setUserEmail(user.getUserEmail());
                userResponse.setUserGsm(user.getUserGsm());
                userResponse.setCropList(cropList);
                userResponse.setLandList(landList);
                return userResponse;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Exception occured while getting user.",e);
            return null;
        }
    }

    @Transactional
    public boolean updateUser(Integer userId,UserRequest userRequest) {
        try {
            User user = userRepository.findByUserId(userId);
            user.setUserGsm(userRequest.getUserGsm());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserName(userRequest.getUserName());
            user.setUserPass(userRequest.getUserPass());
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

    @Transactional
    @Override
    public void deleteUserById(Integer userId) {
        farmerDisinfectionRepository.deleteByUserUserId(userId);
        userRepository.deleteByUserId(userId);
    }

}
