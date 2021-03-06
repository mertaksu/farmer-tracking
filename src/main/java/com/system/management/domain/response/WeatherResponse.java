package com.system.management.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
    private String title;
    private String placeName;
    private List<DailyWeather> dailyWeatherList;
}
