package ru.stazaev.service;

public interface UriGenerator {
    String generateCityCodeUri(String code);

    String generateHourForecastUri(int code);

    String generateDayForecastUri(int code);

    String generateTwelveHourForecast(int city);
    String generateFiveDaysForecastUri(int code);

}
