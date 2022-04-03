package com.sda.weather.location;

import com.sda.weather.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Optional<Location> findLocationById(String cityId) {
        try {
            long id = Long.parseLong(cityId);
            return locationRepository.findById(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    Location createLocation(String city, String region, String country, String longitude, String latitude) {
        if (city == null || city.isBlank()) {
            throw new BadRequestException("Nazwa miasta nie może być pusta");
        }
        if (country == null || country.isBlank()) {
            throw new BadRequestException("Nazwa kraju nie może być pusta");
        }
        if (region != null && region.isBlank()) {
            region = null;
        }

        double longitudeValue = parseValue(longitude);
        double latitudeValue = parseValue(latitude);

        if (longitudeValue > 90 || longitudeValue < -90) {
            throw new BadRequestException("Szerokość geograficzna musi mieścić się w przedziale <-90; 90>");
        }
        if (latitudeValue > 180 || latitudeValue < -180) {
            throw new BadRequestException("Szerokość geograficzna musi mieścić się w przedziale <-180; 180>");
        }

        Location location = new Location(city, region, country, longitudeValue, latitudeValue);
        return locationRepository.save(location);
    }

    private double parseValue(String value) {
        try {
            value = value.replaceAll(",", ".");
            return Double.parseDouble(value);
        }catch (NumberFormatException e) {
            throw new BadRequestException("Szerokość i długość geograficzna muszą być liczbami");
        }
    }

    List<Location> getLocations() {
        return locationRepository.findAll();
    }
}