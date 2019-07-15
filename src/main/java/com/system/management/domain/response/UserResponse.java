package com.system.management.domain.response;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String userName;

    private String userEmail;

    private String userGsm;

    private List<Land> landList;

    private List<Crop> cropList;
}
