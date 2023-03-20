package ru.stazaev.service;

import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;

public interface JsonFormatterService {
    HourlyForecast jsonHourlyFormatter(String string);

    DailyForecast jsonDailyFormatter(String string);
}
