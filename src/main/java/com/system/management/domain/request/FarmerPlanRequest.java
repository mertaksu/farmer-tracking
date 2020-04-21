package com.system.management.domain.request;

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
public class FarmerPlanRequest {

    private String planType;

    private String[] planDate;

    private String cropId;

    private String landId;

}
