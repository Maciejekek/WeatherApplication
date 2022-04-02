package com.sda.weather.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;
    private final ForecastMapper forecastMapper;
    private final ObjectMapper objectMapper;

    public String getForecast(String cityId, String period) {
        try {
            var forecast = forecastService.getForecast(cityId, period);
            var forecastDTO = forecastMapper.asForecastDTO(forecast);
            return objectMapper.writeValueAsString(forecastDTO);
        } catch (Exception e) {
            return String.format("error message: %s}", e.getMessage());
        }
    }
}