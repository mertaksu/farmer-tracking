package com.system.management.service;

import com.system.management.domain.response.WeatherResponse;

import java.util.List;

public interface IWeatherService {
    List<WeatherResponse> getWeather(Integer userId);
}
