package com.sda.weather.forecast;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

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
}