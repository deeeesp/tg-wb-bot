package ru.stazaev.service.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.service.UriGenerator;

@Service
public class UriGeneratorImpl implements UriGenerator {
    private static final String CODE_URI = "https://dataservice.accuweather.com/locations/v1/cities/search";
    private static final String HOURLY_FORECAST_URI = "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
    private static final String DAILY_FORECAST_URI = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private static final String apiKey = "axDKih6w9hAxeB1yuJBuoGLZqU8MjArJ";
    private static final String queryParamApi = "apikey";
    private static final String queryParamCity = "q";
    private static final String queryParamLanguage = "language";
    private static final String language = "ru";

    @Override
    public String generateCityCodeUri(String city) {
        return CODE_URI + "?" + queryParamApi + "=" + apiKey + "&" + queryParamCity + "=" + city;
    }

    @Override
    public String generateHourlyForecastUri(int city) {
        return HOURLY_FORECAST_URI + city + "?" + queryParamApi + "=" + apiKey + "&" + queryParamLanguage + "=" + language;
    }

    @Override
    public String generateDailyForecastUri(int city) {
        return DAILY_FORECAST_URI + city + "?" + queryParamApi + "=" + apiKey + "&" + queryParamLanguage + "=" + language;
    }


}
