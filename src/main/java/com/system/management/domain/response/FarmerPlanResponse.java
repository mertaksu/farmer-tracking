package com.system.management.domain.response;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import com.system.management.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FarmerPlanResponse {
    private int farmerPlanId;

    private String planType;

    private String planDate;

    private String cropName;

    private String landName;
}
