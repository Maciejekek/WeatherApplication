package com.sda.weather.forecast;

public interface ForecastRepository {
    Forecast save(Forecast forecast);
}