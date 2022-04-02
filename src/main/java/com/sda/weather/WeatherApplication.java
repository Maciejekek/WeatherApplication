package com.sda.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.*;
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
        var scanner = new Scanner(System.in);
        var ui = new UI(scanner , locationController);
        ui.run();
    }
}
