package com.sda.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.forecast.client.ForecastClient;
import com.sda.weather.forecast.client.ForecastClientResponseMapper;
import com.sda.weather.location.*;
import com.sda.weather.forecast.*;
import com.sda.weather.ui.UI;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Scanner;

public class WeatherApplication {
    public static void main(String[] args) {
        var registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        var sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
        var locationRepository = new LocationRepositoryImpl(sessionFactory);
        var locationService = new LocationService(locationRepository);
        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var locationMapper = new LocationMapper();
        var locationController = new LocationController(objectMapper, locationService, locationMapper);
        var forecastRepository = new ForecastRepositoryImpl(sessionFactory);
        var forecastResponseMapper = new ForecastClientResponseMapper();
        var forecastClient = new ForecastClient(forecastResponseMapper,objectMapper);
        var forecastService = new ForecastService(locationService, forecastClient, forecastRepository);
        var windDirectionMapper = new WindDirectionMapper();
        var forecastMapper = new ForecastMapper(windDirectionMapper);
        var forecastController = new ForecastController(forecastService, forecastMapper, objectMapper);
        var scanner = new Scanner(System.in);
        UI ui = new UI(scanner , locationController, forecastController);
        ui.run();
    }
}
