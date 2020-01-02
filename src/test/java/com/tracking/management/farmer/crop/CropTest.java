package com.tracking.management.farmer.crop;

import com.system.management.FarmerApplication;
import com.system.management.controller.CropController;
import com.system.management.controller.UserController;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.CropRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.CropResponse;
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
public class CropTest {

    @Autowired
    CropController cropController;

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
    public void a_addCropTest(){
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        CropRequest cropRequest = new CropRequest();
        cropRequest.setUserId(user.get(0).getUserId());
        cropRequest.setCropName("newCrop");
        ResponseEntity<CropResponse> cropResponseResponseEntity = cropController.saveNewCrop(cropRequest);
        Assert.assertNotNull(cropResponseResponseEntity);
        Assert.assertNotNull(cropResponseResponseEntity.getBody());
        Assert.assertNotNull(cropResponseResponseEntity.getBody().getId());
    }

    @Test
    public void b_updateCropTest(){
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        ResponseEntity<List<Crop>> cropList = cropController.getUsersCrop(user.get(0).getUserId());
        Assert.assertNotNull(cropList);
        if(cropList.getBody()!=null && cropList.getBody().get(0)!=null) {
            int cropId = cropList.getBody().get(0).getId();
            ResponseEntity<Boolean> responseEntity = cropController.updateCropName(cropId,"updatedCrop");
            Assert.assertNotNull(responseEntity);
            Assert.assertNotNull(responseEntity.getBody());
            Assert.assertTrue(responseEntity.getBody());
        }
    }

    @Test
    public void c_deleteCropTest(){
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        ResponseEntity<List<Crop>> cropList = cropController.getUsersCrop(user.get(0).getUserId());
        Assert.assertNotNull(cropList);
        if(cropList.getBody()!=null && cropList.getBody().get(0)!=null) {
            int cropId = cropList.getBody().get(0).getId();
            ResponseEntity<Boolean> responseEntity = cropController.deleteCrop(cropId);
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
