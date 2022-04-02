package com.sda.weather.forecast;

import com.sda.weather.exceptions.*;
import com.sda.weather.forecast.client.ForecastClient;
import com.sda.weather.location.LocationService;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@RequiredArgsConstructor
public class ForecastService {

    private final LocationService locationService;
    private final ForecastClient forecastClient;
    private final ForecastRepository forecastRepository;

    Forecast getForecast(String cityId, String period) {
        period = (period == null || period.isBlank()) ? "1" : period;

        int periodValue;
        try {
            periodValue = Integer.parseInt(period);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Dzień prognozy musi być liczbą");
        }
        if (periodValue > 5 || periodValue < 1) {
            throw new BadRequestException("Dzień prognozy musi być liczbą z zakresu 1-5");
        }

        var location = locationService.getLocationById(cityId)
                .orElseThrow(() -> new BadRequestException("Lokalizacja " + cityId + " nie występuje w bazie danych. Najpierw dodaj lokalizację, żeby sprawdzić dla niej prognozę pogody"));

        var forecastDate = LocalDate.now().plusDays(periodValue);
        var forecast = forecastClient.getForecast(location.getCity(), forecastDate)
                .orElseThrow(() -> new InternalServerException("Nie można pobrać prognozy pogody dla miasta: " + location.getCity()));
        forecast.setLocation(location);
        forecast.setCreatedDate(Instant.now());

        return forecastRepository.save(forecast);
    }
}