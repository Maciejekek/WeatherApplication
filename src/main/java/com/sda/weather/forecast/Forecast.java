package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdDate;
    private LocalDate forecastDate;
    private float temperature;
    private int pressure;
    private int humidity;
    private int windSpeed;
    private int windDirection;
    @ManyToOne
    private Location location;
}
