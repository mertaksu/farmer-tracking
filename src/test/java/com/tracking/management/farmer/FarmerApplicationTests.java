package com.tracking.management.farmer;

import com.system.management.FarmerApplication;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.FarmerDisinfectionTransaction;
import com.system.management.domain.entity.Land;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.domain.response.UserResponse;
import com.system.management.repository.FarmerDisinfectionRepository;
import com.system.management.repository.UserRepository;
import com.system.management.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = FarmerApplication.class)
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

	@BeforeAll
	public void setUp() {
		userRepository.deleteAll();
		farmerDisinfectionRepository.deleteAll();

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

	@Order(1)
	@Test
	public void deleteLand() {
		List<Land> landList = landService.getLandsOfUser(userId);
		int initSize = landList.size();
		landService.deleteLand(landList.get(0).getId());
		landList = landService.getLandsOfUser(userId);
		int updatedSize = landList.size();
		Assertions.assertEquals(updatedSize,initSize-1);
	}

	@Order(2)
	@Test
	public void addLand() {
		List<Land> landList = landService.getLandsOfUser(userId);
		int initSize = landList.size();
		landService.addLand(userId,"testLand");
		landList = landService.getLandsOfUser(userId);
		int updatedSize = landList.size();
		Assertions.assertEquals(updatedSize,initSize+1);
	}

	@Order(3)
	@Test
	public void deleteCrop() {
		List<Crop> cropList = cropService.getCropsOfUser(userId);
		int initSize = cropList.size();
		cropService.deleteCrop(cropList.get(0).getId());
		cropList = cropService.getCropsOfUser(userId);
		int updatedSize = cropList.size();
		Assertions.assertEquals(updatedSize,initSize-1);
	}

	@Order(4)
	@Test
	public void addCrop() {
		List<Crop> cropList = cropService.getCropsOfUser(userId);
		int initSize = cropList.size();
		cropService.addCrop(userId,"testCrop");
		cropList = cropService.getCropsOfUser(userId);
		int updatedSize = cropList.size();
		Assertions.assertEquals(updatedSize,initSize+1);
	}

	@Order(5)
	@Test
	public void deleteDisinfection() {
		addDisinfection();
		List<FarmerDisinfectionTransaction> farmerDisinfectionTransactionList = farmerDisinfectionService.getFarmerDisinfectionTransactionsByUserId(userId);
		int initSize = farmerDisinfectionTransactionList.size();
		farmerDisinfectionService.deleteDisinfectionTransactionById(farmerDisinfectionTransactionList.get(0).getId());
		int updatedSize = farmerDisinfectionService.getFarmerDisinfectionTransactionsByUserId(userId).size();
		Assertions.assertEquals(updatedSize,initSize-1);
	}

	@Order(6)
	@Test
	public void addDisinfection() {
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
		Assertions.assertNotEquals(0,responseList.size());
	}

	@Order(7)
	@Test
	public void deleteUser() {
		UserResponse userResponse = userService.getUser(userId);
		Assertions.assertNotNull(userResponse);
		userService.deleteUserById(userId);
		userResponse = userService.getUser(userId);
		Assertions.assertNull(userResponse);
		List<Land> landList = landService.getLandsOfUser(userId);
		List<Crop> cropList = cropService.getCropsOfUser(userId);
		Assertions.assertEquals(0,landList.size());
		Assertions.assertEquals(0,cropList.size());
	}
}
