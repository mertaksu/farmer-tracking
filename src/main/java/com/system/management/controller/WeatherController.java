package com.system.management.controller;

import com.system.management.domain.response.WeatherResponse;
import com.system.management.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final IWeatherService weatherService;

    @GetMapping(value = "/weather/{latitude}/{longitude}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> getHourlyWeatherPerDay(@PathVariable Double latitude,@PathVariable Double longitude) {
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.getWeather(latitude,longitude));
    }
}
