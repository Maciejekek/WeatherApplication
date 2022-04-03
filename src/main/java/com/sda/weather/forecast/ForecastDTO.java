package com.sda.weather.forecast;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
class ForecastDTO {
    private final Long id;
    private final float temperature;
    private final int pressure;
    private final int humidity;
    private final int windSpeed;
    private final String windDirection;
}