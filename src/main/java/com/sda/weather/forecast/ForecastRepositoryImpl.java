package com.sda.weather.forecast;

import com.sda.weather.location.Location;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastRepositoryImpl implements ForecastRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Forecast save(Forecast forecast) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        session.persist(forecast);
        transaction.commit();
        session.close();
        return forecast;
    }

    @Override
    public Optional<Forecast> findByLocationAndForecastDateAndCreationDate(Location location, LocalDate forecastDate, LocalDate creationDate) {
        var session = sessionFactory.openSession();
        var transaction = session.beginTransaction();
        Optional<Forecast> forecast = session.createQuery("SELECT f FROM Forecast f JOIN f.location l WHERE l.id: l.location_id AND f.forecastDate = :forecastDate AND g.creationDate = :creationDate", Forecast.class)
                .setParameter("location_id", location.getId())
                .setParameter("forecastDate", forecastDate)
                .setParameter("creationDate", creationDate)
                .getResultList()
                .stream()
                .findAny();
        transaction.commit();
        session.close();
        return forecast;
    }
}