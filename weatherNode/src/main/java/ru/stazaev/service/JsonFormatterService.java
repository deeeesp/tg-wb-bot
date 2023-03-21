package ru.stazaev.service;

import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;

import java.util.List;

public interface JsonFormatterService {
    HourlyForecast jsonHourFormatter(String string);
    List<HourlyForecast> jsonHoursFormatter(String string);
    DailyForecast jsonDayFormatter(String string);
    List<DailyForecast> jsonDaysFormatter(String string);
}
