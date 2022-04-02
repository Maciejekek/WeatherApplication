package com.sda.weather.forecast.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
class ForecastResponse {

    @JsonProperty("list")
    private List<SingleForecast> singleForecasts;

    @Data
    static class SingleForecast {

        private Main main;
        private Wind wind;

        @JsonProperty("dt_txt")
        private String date;

        @Data
        static class Main {
            private float temp;
            private int pressure;
            private int humidity;
        }

        @Data
        static class Wind {
            private float speed;
            private float deg;
        }
    }
}