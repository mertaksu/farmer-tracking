package com.tracking.management.farmer.user;

import com.system.management.FarmerApplication;
import com.system.management.controller.UserController;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.UserRequest;
import com.system.management.repository.UserRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = FarmerApplication.class)
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Test
    public void a_createUserTest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("test");
        userRequest.setUserEmail("test@test.com");
        userRequest.setUserGsm("909999999999");
        userRequest.setUserPass("test123");
        List<Crop> cropList = new ArrayList<>();
        cropList.add(new Crop("testCrop1"));
        cropList.add(new Crop("testCrop2"));
        userRequest.setCropList(cropList);
        List<Land> landList = new ArrayList<>();
        landList.add(new Land("testLand1"));
        landList.add(new Land("testLand2"));
        userRequest.setLandList(landList);
        ResponseEntity<Integer> responseEntity = userController.saveUser(userRequest);
        Assert.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void b_updateUserTest() {
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("test1");
        userRequest.setUserEmail("test1@test.com");
        userRequest.setUserGsm("909999999990");
        userRequest.setUserPass("test123!");
        List<Crop> cropList = new ArrayList<>();
        cropList.add(new Crop("testCrop1"));
        cropList.add(new Crop("testCrop2"));
        userRequest.setCropList(cropList);
        List<Land> landList = new ArrayList<>();
        landList.add(new Land("testLand1"));
        landList.add(new Land("testLand2"));
        userRequest.setLandList(landList);
        ResponseEntity<Boolean> responseEntity = userController.updateUser(user.get(0).getUserId(),userRequest);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody());
    }

    @Test
    public void c_deleUserTest() {
        List<User> user = userRepository.findByUserNameAndUserPass("test1","test123!");
        ResponseEntity<Boolean> responseEntity = userController.deleteUser(user.get(0).getUserId());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody());
    }
}
