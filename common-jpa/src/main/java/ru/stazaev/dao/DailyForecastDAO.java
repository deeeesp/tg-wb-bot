package ru.stazaev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DailyForecastDAO extends JpaRepository<DailyForecast,Long> {
    List<DailyForecast> findDailyForecastByCode(int code);
    Optional<DailyForecast> findDailyForecastByCodeAndDate(int code, Date date);

    @Query("select d from DailyForecast d where d.date >= :date and d.code =:code")
    List<DailyForecast> getDailyForecast(@Param("code") int code, @Param("date") Date date);

    @Transactional
    void deleteAllByCode(int code);
}
