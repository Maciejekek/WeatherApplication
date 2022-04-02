package com.sda.weather.location;

public class LocationMapper {

    LocationDTO asLocationDTO(Location location) {
        var locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setCity(location.getCity());
        locationDTO.setLongitude(location.getLongitude());
        locationDTO.setLatitude(location.getLatitude());
        return locationDTO;
    }
}