package com.tracking.management.farmer.land;

import com.system.management.FarmerApplication;
import com.system.management.controller.LandController;
import com.system.management.controller.UserController;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.LandRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.LandResponse;
import com.system.management.repository.UserRepository;
import org.junit.*;
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
public class LandTest {

    @Autowired
    LandController landController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;

    @Before
    public void setup() {
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
        userController.saveUser(userRequest);
    }

    @Test
    public void a_addLandTest() {
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        LandRequest landRequest = new LandRequest();
        landRequest.setLandName("newLand");
        landRequest.setUserId(user.get(0).getUserId());
        ResponseEntity<LandResponse> landResponseResponseEntity = landController.saveNewLand(landRequest);
        Assert.assertNotNull(landResponseResponseEntity);
        Assert.assertNotNull(landResponseResponseEntity.getBody());
        Assert.assertNotNull(landResponseResponseEntity.getBody().getId());
    }

    @Test
    public void b_updateLandTest() {
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        ResponseEntity<List<Land>> landList = landController.getUsersLand(user.get(0).getUserId());
        Assert.assertNotNull(landList);
        if(landList.getBody()!=null && landList.getBody().get(0)!=null) {
           int landId = landList.getBody().get(0).getId();
           ResponseEntity<Boolean> responseEntity = landController.updateLandName(landId,"updatedLand");
           Assert.assertNotNull(responseEntity);
           Assert.assertNotNull(responseEntity.getBody());
           Assert.assertTrue(responseEntity.getBody());
        }
    }

    @Test
    public void c_deleteLandTest() {
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        ResponseEntity<List<Land>> landList = landController.getUsersLand(user.get(0).getUserId());
        Assert.assertNotNull(landList);
        if(landList.getBody()!=null && landList.getBody().get(0)!=null) {
            ResponseEntity<Boolean> responseEntity = landController.deleteLand(landList.getBody().get(0).getId());
            Assert.assertNotNull(responseEntity);
            Assert.assertNotNull(responseEntity.getBody());
            Assert.assertTrue(responseEntity.getBody());
        }
    }

    @After
    public void after() {
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        user.forEach(user1 -> {
            userController.deleteUser(user1.getUserId());
        });
    }
}
