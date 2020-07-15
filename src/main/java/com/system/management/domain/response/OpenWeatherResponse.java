package com.system.management.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpenWeatherResponse {
    private double lat;
    private double lon;
    private List<Hourly> hourly;
    private List<Daily> daily;
}
