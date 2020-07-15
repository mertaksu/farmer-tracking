package com.system.management.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyWeather {
    private String hour;
    private String icon;
    private String centigrade;
    private String desc;
}
