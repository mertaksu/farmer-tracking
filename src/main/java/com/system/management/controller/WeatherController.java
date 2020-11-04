package com.system.management.controller;

import com.system.management.domain.response.WeatherResponse;
import com.system.management.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final IWeatherService weatherService;

    @GetMapping(value = "/weather",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherResponse>> getHourlyWeatherPerDay(HttpServletRequest request) {
        Integer userId = Integer.parseInt((String)request.getAttribute("userId"));
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.getWeather(userId));
    }
}
