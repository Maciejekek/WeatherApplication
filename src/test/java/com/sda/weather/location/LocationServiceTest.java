package com.sda.weather.location;

import com.sda.weather.exceptions.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class LocationServiceTest {
    private static LocationService locationService;
    private static LocationRepositoryMock locationRepository;

    @BeforeAll
    static void setUp() {
        locationRepository = new LocationRepositoryMock();
        locationService = new LocationService(locationRepository);
    }

    @AfterEach
    void tearDown() {
        locationRepository.clear();
    }

    @Test
    void createNewLocation_createsLocation() {
        // when
        Location result = locationService.createLocation("Klodzko", "DolnySlask", "Polska", "50.5", "16.5");
        // then
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("Klodzko");
        assertThat(result.getRegion()).hasValue("DolnySlask");
        assertThat(result.getCountry()).isEqualTo("Polska");
        assertThat(result.getLongitude()).isEqualTo(50.5f);
        assertThat(result.getLatitude()).isEqualTo(16.5f);
    }

    @Test
    void createNewLocation_whenRegionIsBlank_createsLocation() {
        // when
        Location result = locationService.createLocation("Klodzko", " ", "Polska", "50.5", "16.5");
        // then
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo("Klodzko");
        assertThat(result.getRegion()).isEmpty();
        assertThat(result.getCountry()).isEqualTo("Polska");
        assertThat(result.getLongitude()).isEqualTo(50.5f);
        assertThat(result.getLatitude()).isEqualTo(16.5f);
    }

    @Test
    void createNewLocation_whenCityIsBlank_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation(" ", "DolnySlask", "Polska", "50.5", "16.5"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenCountryIsBlank_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", " ", "50.5", "16.5"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenLongitudeIsBlank_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", "Polska", "", "16.5"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenLatitudeIsBlank_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", "Polska", "50.5", ""));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenLongitudeIsMoreThan90_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", "Polska", "91.0", "16.5"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenLongitudeIsLessThan90Negative_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", "Polska", "-91.0", "16.5"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenLatitudeIsMoreThan180_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", "Polska", "50.5", "181"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void createNewLocation_whenLatitudeIsLessThan180Negative_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> locationService.createLocation("Klodzko", "DolnySlask", "Polska", "50.5", "-181"));
        // then
        assertThat(throwable).isInstanceOf(BadRequestException.class);
    }

    @Test
    void getLocations_returnsLocations() {
        // given
        locationRepository.save(new Location("Klodzko", "DolnySlask", "Polska", 50.5f, 16.5f));
        locationRepository.save(new Location("Warszawa", "Mazowieckie", "Polska", 40.5f, 50.5f));
        // when
        List<Location> result = locationRepository.findAll();
        // then
        assertThat(result).hasSize(2);
    }
}