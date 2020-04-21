package com.system.management.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LandRequest {
    private String landName;
    private Double latitude;
    private Double longitude;
}
