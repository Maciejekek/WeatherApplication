package com.sda.weather.forecast.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.forecast.Forecast;
import com.sda.weather.location.Location;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastClient {

    private static final String URL = ("https://api.openweathermap.org/data/2.5/weather?lat=%d&lon=%d&appid=9eee5d29c32bb007a35bf68e62cc47b0"); //todo api impl
    private final ForecastResponseMapper forecastResponseMapper;
    private final ObjectMapper objectMapper;

    public Optional<Forecast> getForecast(LocalDate forecastDate, Double latitude, Double longitude) {
        var httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(URL,latitude,longitude)))
                .build();
        var httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            var forecastResponse = objectMapper.readValue(responseBody, ForecastResponse.class);
            var predictionDate = forecastDate.atTime(12, 00);
            return forecastResponse.getSingleForecasts().stream()
                    .filter(f -> forecastResponseMapper.asLocalDateTime(f.getDate()).isEqual(predictionDate))
                    .map(forecastResponseMapper::asForecast)
                    .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}