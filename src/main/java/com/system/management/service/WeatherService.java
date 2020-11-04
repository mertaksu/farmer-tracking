package com.system.management.service;

import com.system.management.domain.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService implements IWeatherService {

    private static final SimpleDateFormat dayPattern = new SimpleDateFormat("EEE, d MMM", new Locale("TR"));

    private static final String weatherApiWeatherUrl = "https://api.openweathermap.org/data/2.5/onecall?lat={latitude}&lon={longitude}&units=metric&lang=tr&appid=5c004551d520aa01b11e7637439e9217";

    private final ILandService landService;

    @Override
    public List<WeatherResponse> getWeather(Integer userId) {
        List<LandCoordinate> landCoordinateList = getLandCoordinateList(userId);
        List<WeatherResponse> weatherResponseList = new ArrayList<>();
        try {
            for (LandCoordinate landCoordinate:landCoordinateList) {
                long startTime = System.currentTimeMillis();
                WeatherResponse weatherResponse = new WeatherResponse();
                CompletableFuture<Void> weatherResponseCompletableFuture = new CompletableFuture<>();
                try {
                    Map<String, Double> params = new HashMap<>();
                    params.put("latitude", landCoordinate.getLatitude());
                    params.put("longitude",landCoordinate.getLongitude());
                    RestTemplate restTemplate = new RestTemplate();

                    weatherResponseCompletableFuture = CompletableFuture.runAsync(() -> {
                        List<DailyWeather> dailyWeatherList = new ArrayList<>();
                        long start = System.currentTimeMillis();
                        OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(weatherApiWeatherUrl, OpenWeatherResponse.class,params);
                        log.info("Api called for weather with delta: {}",System.currentTimeMillis()-start);
                        if(openWeatherResponse!=null) {

                            if(openWeatherResponse.getDaily()!=null) {
                                openWeatherResponse.getDaily().forEach(daily -> {
                                    DailyWeather dailyWeather = new DailyWeather();
                                    Date date = new Date(daily.getDt()*1000);
                                    String day = dayPattern.format(date);
                                    dailyWeather.setDay(day);
                                    dailyWeather.setCentigrade((int)daily.getTemp().getDay()+"°C");
                                    dailyWeather.setDesc(daily.getWeather().get(0).getDescription());
                                    dailyWeather.setIcon(daily.getWeather().get(0).getIcon());
                                    dailyWeatherList.add(dailyWeather);
                                });
                            }
                        }
                        weatherResponse.setDailyWeatherList(dailyWeatherList);
                        weatherResponse.setPlaceName(landCoordinate.getPlaceName());
                        weatherResponse.setTitle(landCoordinate.getTitle());
                    });

                } catch (Exception e) {
                    log.error("Exception occured while getting weather response ",e);
                }

                weatherResponseCompletableFuture.join();
                log.info("Total weather service delta:{}",System.currentTimeMillis()-startTime);
                weatherResponseList.add(weatherResponse);
            }

        } catch (Exception e) {
            log.error("Exception occured.",e);
        }
        return weatherResponseList;
    }

    private List<LandCoordinate> getLandCoordinateList(Integer userId) {
        return landService.getLandsCoordOfUser(userId);
    }
}
