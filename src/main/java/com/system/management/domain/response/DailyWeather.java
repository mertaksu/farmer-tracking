package com.system.management.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyWeather {
    private String day;
    private String icon;
    private String centigrade;
    private String desc;
}
