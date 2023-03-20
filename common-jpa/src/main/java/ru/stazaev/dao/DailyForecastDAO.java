package ru.stazaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stazaev.entity.DailyForecast;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DailyForecastDAO extends JpaRepository<DailyForecast,Long> {
    List<DailyForecast> findDailyForecastByCode(int code);
    Optional<DailyForecast> findDailyForecastByCodeAndDate(int code, Date date);
}
