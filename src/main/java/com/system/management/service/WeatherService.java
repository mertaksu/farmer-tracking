package com.system.management.service;

import com.system.management.domain.response.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class WeatherService implements IWeatherService {

    private static final SimpleDateFormat dayPattern = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat hourPattern = new SimpleDateFormat("HH:mm");

    private static final String apiUrl = "https://api.openweathermap.org/data/2.5/onecall?lat={latitude}&lon={longitude}&units=metric&lang=tr&appid=5c004551d520aa01b11e7637439e9217";

    @Override
    public WeatherResponse getWeather(Double latitude, Double longitude) {
        WeatherResponse weatherResponse = new WeatherResponse();
        try {
            Map<Long,List<HourlyWeather>> hourlyWetherPerDay = new TreeMap<>();
            List<DailyWeather> dailyWeatherList = new ArrayList<>();
            Map<String, Double> params = new HashMap<>();
            params.put("latitude", latitude);
            params.put("longitude",longitude);
            RestTemplate restTemplate = new RestTemplate();
            OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(apiUrl, OpenWeatherResponse.class,params);
            if(openWeatherResponse!=null) {
                if(openWeatherResponse.getHourly()!=null) {
                    openWeatherResponse.getHourly().forEach(hourly -> {
                        Date date = new Date(hourly.getDt()*1000);
                        String day = dayPattern.format(date);
                        if(!hourlyWetherPerDay.containsKey(Long.valueOf(day))) {
                            List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
                            hourlyWeatherList.add(hourlyWeather(hourly));
                            hourlyWetherPerDay.put(Long.valueOf(day),hourlyWeatherList);
                        } else {
                            hourlyWetherPerDay.get(Long.valueOf(day)).add(hourlyWeather(hourly));
                        }
                    });
                }

                if(openWeatherResponse.getDaily()!=null) {
                    openWeatherResponse.getDaily().forEach(daily -> {
                        DailyWeather dailyWeather = new DailyWeather();
                        Date date = new Date(daily.getDt()*1000);
                        String day = dayPattern.format(date);
                        dailyWeather.setDay(day);
                        dailyWeather.setCentigrade(String.valueOf((int)daily.getTemp().getDay()));
                        dailyWeather.setDesc(daily.getWeather().get(0).getDescription());
                        dailyWeather.setIcon(daily.getWeather().get(0).getIcon());
                        dailyWeatherList.add(dailyWeather);
                    });
                }
            }
            weatherResponse.setDailyWeatherList(dailyWeatherList);
            weatherResponse.setHourlyWetherPerDay(hourlyWetherPerDay);
        } catch (Exception e) {
            log.error("Exception occured while getting weather response ",e);
        }
        return weatherResponse;
    }

    private HourlyWeather hourlyWeather(Hourly hourly) {
        HourlyWeather hourlyWeather = new HourlyWeather();
        try {
            hourlyWeather.setHour(hourPattern.format(new Date(hourly.getDt()*1000)));
            hourlyWeather.setCentigrade(String.valueOf((int)hourly.getTemp()));
            hourlyWeather.setIcon(hourly.getWeather().get(0).getIcon());
            hourlyWeather.setDesc(hourly.getWeather().get(0).getDescription());
        } catch (Exception e) {
            log.error("Error while setting");
        }
        return hourlyWeather;
    }
}
