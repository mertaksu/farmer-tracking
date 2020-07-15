package com.system.management.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class Hourly {
    private long dt;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private int clouds;
    private double wind_speed;
    private int wind_deg;
    private List<Weather> weather;
}
