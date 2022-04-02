package com.sda.weather.forecast.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
class ForecastResponse {

    @JsonProperty()
    private List<SingleForecast> singleForecasts;

    @Data
    static class SingleForecast {
        private float temp;
        private int pressure;
        private int humidity;
        private float speed;
        private float deg;
        @JsonProperty()
        private String date;
    }
}