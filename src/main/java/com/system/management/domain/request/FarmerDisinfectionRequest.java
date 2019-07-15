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
public class FarmerDisinfectionRequest {

    private String pharmacyType;

    private Date pharmacyStartDate;

    private Date pharmacyFinishDate;

    private Integer everyFewDays;

    private Crop crop;

    private Land land;

    private Integer userId;

    private Boolean status;
}
