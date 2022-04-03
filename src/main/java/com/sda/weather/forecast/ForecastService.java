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
            throw new BadRequestException("Dzien prognozy musi być liczba");
        }
        if (periodValue > 7 || periodValue < 1) {
            throw new BadRequestException("dzien prognozy musi być liczbą z zakresu 1-7");
        }

        var location = locationService.findLocationById(cityId)
                .orElseThrow(() -> new BadRequestException("Lokalizacja " + cityId + " nie wystepuje w bazie danych, dajpierw dodaj lokalizację, zeby sprawdzic dla niej prognoze pogody"));

        var forecastDate = LocalDate.now().plusDays(periodValue);
        var forecast = forecastClient.getForecast(forecastDate, location.getLatitude(), location.getLongitude())
                .orElseThrow(() -> new InternalServerException("Nie mozna pobrac prognozy pogody dla miasta: " + location.getCity()));
        forecast.setLocation(location);
        forecast.setCreatedDate(LocalDate.now());

        return forecastRepository.save(forecast);
    }
}