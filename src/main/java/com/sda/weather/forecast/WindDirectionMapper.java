package com.sda.weather.forecast;

import com.sda.weather.exceptions.InternalServerException;

public class WindDirectionMapper {

    private final static String[] DIRECTIONS = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};

    String mapWindDirection(int windDirection) {
        if (windDirection > 360 || windDirection < 0) {
            throw new InternalServerException("Kierunek wiatru nie moze zostac zmapowany: " + windDirection);
        }

        double val = Math.floor((windDirection / 22.5) + 0.5);
        return DIRECTIONS[(int) (val % 16)];
    }
}