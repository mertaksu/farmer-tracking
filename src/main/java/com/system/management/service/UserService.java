package com.system.management.service;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.RegisterRequest;
import com.system.management.domain.response.UserResponse;
import com.system.management.repository.CropRepository;
import com.system.management.repository.FarmerPlanRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    FarmerPlanRepository farmerPlanRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public int saveUser(RegisterRequest registerRequest) {
        try {
            User user = userRepository.findByUserName(registerRequest.getUserName());
            if(user!=null) {
                return -1;
            } else {
                user = new User();
                user.setUserName(registerRequest.getUserName());
                user.setUserEmail(registerRequest.getUserEmail());
                user.setUserGsm(registerRequest.getUserGsm());
                user.setUserPass(bCryptPasswordEncoder.encode(registerRequest.getUserPass()));
                User savedUser = userRepository.save(user);

                log.info("New User Saved. UserName: {}",savedUser.getUserName());
                return savedUser.getUserId();
            }
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
    public boolean updateUser(Integer userId, RegisterRequest registerRequest) {
        try {
            User user = userRepository.findByUserId(userId);
            user.setUserGsm(registerRequest.getUserGsm());
            user.setUserEmail(registerRequest.getUserEmail());
            user.setUserName(registerRequest.getUserName());
            user.setUserPass(registerRequest.getUserPass());

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
        farmerPlanRepository.deleteByUserUserId(userId);
        userRepository.deleteByUserId(userId);
        cropRepository.deleteByUserUserId(userId);
        landRepository.deleteByUserUserId(userId);
    }

}
