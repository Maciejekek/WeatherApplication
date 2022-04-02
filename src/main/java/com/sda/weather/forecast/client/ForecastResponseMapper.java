package com.sda.weather.forecast.client;

import com.sda.weather.forecast.Forecast;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ForecastResponseMapper {

    Forecast asForecast(ForecastResponse.SingleForecast singleForecast) {
        var forecastDate = asLocalDateTime(singleForecast.getDate());
        var forecastDateInstant = forecastDate.atZone(ZoneId.systemDefault()).toInstant();
        var forecast = new Forecast();
        forecast.setHumidity(singleForecast.getHumidity());
        forecast.setPressure(singleForecast.getPressure());
        forecast.setTemperature(singleForecast.getTemp());
        forecast.setWindDirection((int) singleForecast.getDeg());
        forecast.setWindSpeed((int) singleForecast.getSpeed());
        forecast.setForecastDate(forecastDateInstant);
        return forecast;
    }
    LocalDateTime asLocalDateTime(String date) {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }
}
