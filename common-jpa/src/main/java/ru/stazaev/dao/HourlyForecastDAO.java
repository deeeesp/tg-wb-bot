package ru.stazaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.entity.HourlyForecast;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HourlyForecastDAO extends JpaRepository<HourlyForecast, Long> {
    List<HourlyForecast> findHourlyForecastByCode(int code);

    Optional<HourlyForecast> findHourlyForecastByCodeAndDate(int code, Date date);

    @Query("select h from HourlyForecast h where h.date >= :date and h.code =:code")
    List<HourlyForecast> getHourForecast(@Param("code") int code, @Param("date") Date date);

    @Transactional
    void deleteAllByCode(int code);
}
