package com.system.management.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class WeatherResponse {
    Map<String,List<HourlyWeather>> hourlyWetherPerDay;
    List<DailyWeather> dailyWeatherList;
}
