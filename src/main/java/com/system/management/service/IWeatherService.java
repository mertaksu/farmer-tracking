package com.system.management.service;

import com.system.management.domain.response.WeatherResponse;

public interface IWeatherService {
    WeatherResponse getWeather(Double latitude, Double longitude);
}
