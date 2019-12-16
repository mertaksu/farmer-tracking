package com.system.management.domain.response;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String userName;

    private String userEmail;

    private String userGsm;

    private List<Land> landList;

    private List<Crop> cropList;
}
