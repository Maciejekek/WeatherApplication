package com.sda.weather.forecast.client;

import com.sda.weather.forecast.Forecast;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForecastClientResponseMapper {

    Forecast asForecast(ForecastClientResponseDTO.DailyForecast dailyForecast) {
        var forecast = new Forecast();
        forecast.setHumidity(dailyForecast.getHumidity());
        forecast.setPressure(dailyForecast.getPressure());
        forecast.setWindDirection(dailyForecast.getWindDeg());
        forecast.setWindSpeed(dailyForecast.getWindSpeed());
        forecast.setTemperature(dailyForecast.getTemperature().getDay());
        return forecast;

    }
    LocalDateTime asLocalDateTime(String date) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
