package com.sda.weather.forecast.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.forecast.Forecast;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastClient {

    private static final String URL =
            ("https://api.openweathermap.org/data/2.5/onecall?lat=%d&lon=%d&exclude=current,minutely,hourly&units=metric&appid=9eee5d29c32bb007a35bf68e62cc47b0");
    private final ForecastClientResponseMapper forecastClientResponseMapper;
    private final ObjectMapper objectMapper;

    public Optional<Forecast> getForecast(LocalDate forecastDate, Double lat, Double lon) {
        var httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(URL, lat, lon)))
                .build();
        var httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            var forecastResponse = objectMapper.readValue(responseBody, ForecastClientResponseDTO.class);
            return forecastResponse.getDaily().stream()
                    .filter(l -> forecastClientResponseMapper.equals(forecastDate))
                    .map(forecastClientResponseMapper::asForecast)
                    .findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}