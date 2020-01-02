package com.system.management.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropResponse {
    private Integer id;

    private String cropName;

    private Integer userId;
}
