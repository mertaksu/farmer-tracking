package com.system.management.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LandResponse {
    private Integer id;

    private String landName;

    private Integer userId;
}
