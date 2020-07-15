package com.system.management.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class Daily {
    private long dt;
    private Temp temp;
    private List<Weather> weather;
}
