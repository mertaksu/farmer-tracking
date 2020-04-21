package com.system.management.domain.request;

import com.system.management.domain.entity.Crop;
import com.system.management.domain.entity.Land;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String userName;

    private String userEmail;

    private String userGsm;

    private String userPass;
}
