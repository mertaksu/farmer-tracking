package com.tracking.management.farmer.disinfection;

import com.system.management.FarmerApplication;
import com.system.management.controller.FarmerDisinfectionController;
import com.system.management.controller.UserController;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.repository.CropRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = FarmerApplication.class)
@RunWith(SpringRunner.class)
public class DisinfectionTest {

    @Autowired
    FarmerDisinfectionController farmerDisinfectionController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;

    @Autowired
    LandRepository landRepository;

    @Autowired
    CropRepository cropRepository;

    @Before
    public void setupClass() {
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
    public void a_addDisinfection() {
        FarmerDisinfectionRequest farmerDisinfectionRequest = new FarmerDisinfectionRequest();
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        farmerDisinfectionRequest.setUserId(user.get(0).getUserId());
        farmerDisinfectionRequest.setStatus(false);
        farmerDisinfectionRequest.setPharmacyType("Disinfection");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,Calendar.JANUARY,1);
        farmerDisinfectionRequest.setPharmacyStartDate(calendar.getTime());
        calendar.set(2020,Calendar.FEBRUARY,30);
        farmerDisinfectionRequest.setPharmacyFinishDate(calendar.getTime());
        farmerDisinfectionRequest.setEveryFewDays(10);
        farmerDisinfectionRequest.setCrop(cropRepository.findByUserUserId(user.get(0).getUserId()).get(0));
        farmerDisinfectionRequest.setLand(landRepository.findByUserUserId(user.get(0).getUserId()).get(0));
        ResponseEntity<List<FarmerDisinfectionResponse>> responseList = farmerDisinfectionController.saveFarmerDisinfections(farmerDisinfectionRequest);
        Assert.assertNotNull(responseList);
        Assert.assertNotNull(responseList.getBody());
        Assert.assertNotEquals(0,responseList.getBody().size());
    }

    @Test
    public void b_getDisinfection() {
        a_addDisinfection();
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        ResponseEntity<List<FarmerDisinfectionTransaction>> responseList = farmerDisinfectionController.getFarmerDisinfections(user.get(0).getUserId());
        Assert.assertNotNull(responseList);
        Assert.assertNotNull(responseList.getBody());
        Assert.assertNotEquals(0,responseList.getBody().size());
    }

    @Test
    public void c_deleteDisinfection() {
        a_addDisinfection();
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        ResponseEntity<List<FarmerDisinfectionTransaction>> responseList = farmerDisinfectionController.getFarmerDisinfections(user.get(0).getUserId());
        Assert.assertNotNull(responseList);
        Assert.assertNotNull(responseList.getBody());
        int listSize = responseList.getBody().size();
        farmerDisinfectionController.deleteDisinfection(responseList.getBody().get(0).getId());
        responseList = farmerDisinfectionController.getFarmerDisinfections(user.get(0).getUserId());
        Assert.assertNotNull(responseList.getBody());
        Assert.assertEquals(responseList.getBody().size(),listSize-1);
    }

    @After
    public void after() {
        List<User> user = userRepository.findByUserNameAndUserPass("test","test123");
        user.forEach(user1 -> {
            userController.deleteUser(user1.getUserId());
        });
    }
}
