package ru.stazaev.service;

public interface UriGenerator {
    String generateCityCodeUri(String code);

    String generateHourlyForecastUri(int code);

    String generateDailyForecastUri(int code);

}
