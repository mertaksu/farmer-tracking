package com.tracking.management.farmer;

import com.system.management.FarmerApplication;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.entity.Land;
import com.system.management.domain.request.CropRequest;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.request.LandRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.CropResponse;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.domain.response.UserResponse;
import com.system.management.repository.FarmerDisinfectionRepository;
import com.system.management.repository.UserRepository;
import com.system.management.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = FarmerApplication.class)
@RunWith(SpringRunner.class)
public class FarmerApplicationTests {

	@Autowired
	IFarmerDisinfectionService farmerDisinfectionService;

	FarmerDisinfectionRequest farmerDisinfectionRequest;

	@Autowired
	IUserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FarmerDisinfectionRepository farmerDisinfectionRepository;

	@Autowired
	ILandService landService;

	@Autowired
	ICropService cropService;

	int userId = 0;

	@Before
	public void setUp() {
		farmerDisinfectionRepository.deleteAll();
		userRepository.deleteAll();

		UserRequest userRequest = new UserRequest();
		userRequest.setUserName("mertaksu");
		userRequest.setUserPass("1234Aa");
		userRequest.setUserEmail("mertaksu@outlook.com");
		userRequest.setUserGsm("905448378867");

		List<Crop> cropList = new ArrayList<>();
		cropList.add(new Crop("Zeytin"));
		cropList.add(new Crop("Erik"));
		cropList.add(new Crop("Seftali"));
		userRequest.setCropList(cropList);

		List<Land> landList = new ArrayList<>();
		landList.add(new Land("Çakırca"));
		landList.add(new Land("Boyalıca"));
		userRequest.setLandList(landList);

		userId = userService.saveUser(userRequest);

	}

	@Test
	public void a_addLand() {
		List<Land> landList = landService.getLandsOfUser(userId);
		int initSize = landList.size();
		LandRequest landRequest = new LandRequest("testLand",userId);
		landService.addLand(landRequest);
		landList = landService.getLandsOfUser(userId);
		int updatedSize = landList.size();
		Assert.assertEquals(updatedSize,initSize+1);
	}

	@Test
	public void b_deleteLand() {
		List<Land> landList = landService.getLandsOfUser(userId);
		int initSize = landList.size();
		landService.deleteLand(landList.get(0).getId());
		landList = landService.getLandsOfUser(userId);
		int updatedSize = landList.size();
		Assert.assertEquals(updatedSize,initSize-1);
	}

	@Test
	public void c_addCrop() {
		List<Crop> cropList = cropService.getCropsOfUser(userId);
		int initSize = cropList.size();
		CropRequest cropRequest = new CropRequest();
		cropRequest.setCropName("testCrop");
		cropRequest.setUserId(userId);
		CropResponse cropResponse = cropService.addCrop(cropRequest);
		cropList = cropService.getCropsOfUser(userId);
		int updatedSize = cropList.size();
		Assert.assertEquals(updatedSize,initSize+1);
		Assert.assertNotNull(cropResponse);
		Assert.assertNotNull(cropResponse.getId());
	}


	@Test
	public void d_deleteCrop() {
		List<Crop> cropList = cropService.getCropsOfUser(userId);
		int initSize = cropList.size();
		cropService.deleteCrop(cropList.get(0).getId());
		cropList = cropService.getCropsOfUser(userId);
		int updatedSize = cropList.size();
		Assert.assertEquals(updatedSize,initSize-1);
	}

	@Test
	public void e_addDisinfection() {
		farmerDisinfectionRequest = new FarmerDisinfectionRequest();
		farmerDisinfectionRequest.setUserId(userId);
		farmerDisinfectionRequest.setStatus(false);
		farmerDisinfectionRequest.setPharmacyType("Disinfection");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2019,Calendar.JULY,1);
		farmerDisinfectionRequest.setPharmacyStartDate(calendar.getTime());
		calendar.set(2019,Calendar.AUGUST,30);
		farmerDisinfectionRequest.setPharmacyFinishDate(calendar.getTime());
		farmerDisinfectionRequest.setEveryFewDays(10);
		farmerDisinfectionRequest.setCrop(cropService.getCropsOfUser(userId).get(0));
		farmerDisinfectionRequest.setLand(landService.getLandsOfUser(userId).get(0));
		List<FarmerDisinfectionResponse> responseList = farmerDisinfectionService.saveNewFarmerDisinfection(farmerDisinfectionRequest);
		Assert.assertNotEquals(0,responseList.size());
	}

	@Test
	public void f_deleteDisinfection() {
		farmerDisinfectionRepository.deleteAll();
		List<FarmerDisinfectionTransaction> farmerDisinfectionTransactionList = farmerDisinfectionRepository.findByUserUserId(userId);
		Assert.assertEquals(0,farmerDisinfectionTransactionList.size());
	}


	@Test
	public void g_deleteUser() {
		UserResponse userResponse = userService.getUser(userId);
		Assert.assertNotNull(userResponse);
		userService.deleteUserById(userId);
		userResponse = userService.getUser(userId);
		Assert.assertNull(userResponse);
		List<Land> landList = landService.getLandsOfUser(userId);
		List<Crop> cropList = cropService.getCropsOfUser(userId);
		Assert.assertEquals(0,landList.size());
		Assert.assertEquals(0,cropList.size());
	}

}
