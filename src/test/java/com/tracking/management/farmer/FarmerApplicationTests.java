package com.tracking.management.farmer;

import com.system.management.FarmerApplication;
import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.request.FarmerDisinfectionRequest;
import com.system.management.domain.request.UserRequest;
import com.system.management.domain.response.FarmerDisinfectionResponse;
import com.system.management.repository.CropRepository;
import com.system.management.repository.FarmerDisinfectionRepository;
import com.system.management.repository.LandRepository;
import com.system.management.repository.UserRepository;
import com.system.management.service.FarmerDisinfectionService;
import com.system.management.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FarmerApplication.class)
public class FarmerApplicationTests {

	@Autowired
	FarmerDisinfectionService farmerDisinfectionService;

	FarmerDisinfectionRequest farmerDisinfectionRequest;

	@Autowired
	UserService userService;

	@Autowired
	LandRepository landRepository;

	@Autowired
	CropRepository cropRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FarmerDisinfectionRepository farmerDisinfectionRepository;

	int userId = 0;

	@Before
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
		landList.add(new Land("Iznik"));
		landList.add(new Land("Yenisehir"));
		userRequest.setLandList(landList);

		userId = userService.saveUser(userRequest);

	}

	@Test
	public void saveNewDisinfection() {
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
		farmerDisinfectionRequest.setCrop(cropRepository.findByUserUserId(userId).get(0));
		farmerDisinfectionRequest.setLand(landRepository.findByUserUserId(userId).get(0));
		List<FarmerDisinfectionResponse> responseList = farmerDisinfectionService.saveNewFarmerDisinfection(farmerDisinfectionRequest);
		Assert.assertNotEquals(0,responseList.size());
	}

}
