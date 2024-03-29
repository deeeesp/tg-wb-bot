package ru.stazaev.service.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.service.UriGenerator;

@Service
public class UriGeneratorImpl implements UriGenerator {
    private static final String CODE_URI = "https://dataservice.accuweather.com/locations/v1/cities/search";
    private static final String HOUR_FORECAST_URI = "https://dataservice.accuweather.com/forecasts/v1/hourly/1hour/";
    private static final String TWELVE_HOURS_FORECAST_URI = "https://dataservice.accuweather.com/forecasts/v1/hourly/12hour/";
    private static final String DAY_FORECAST_URI = "https://dataservice.accuweather.com/forecasts/v1/daily/1day/";
    private static final String FIVE_DAYS_FORECAST_URI = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private static final String apiKey = "axDKih6w9hAxeB1yuJBuoGLZqU8MjArJ";
//    axDKih6w9hAxeB1yuJBuoGLZqU8MjArJ
//    C3iBJ1zJ0upNcO8h76JFXGy8PopWv3Gl
    private static final String queryParamApi = "apikey";
    private static final String queryParamCity = "q";
    private static final String queryParamLanguage = "language";
    private static final String language = "ru";

    @Override
    public String generateCityCodeUri(String city) {
        return CODE_URI + "?" + queryParamApi + "=" + apiKey + "&" + queryParamCity + "=" + city;
    }

    @Override
    public String generateHourForecastUri(int city) {
        return HOUR_FORECAST_URI + city + "?" + queryParamApi + "=" + apiKey + "&" + queryParamLanguage + "=" + language;
    }

    @Override
    public String generateDayForecastUri(int city) {
        return DAY_FORECAST_URI + city + "?" + queryParamApi + "=" + apiKey + "&" + queryParamLanguage + "=" + language;
    }

    @Override
    public String generateTwelveHourForecast(int city) {
        return TWELVE_HOURS_FORECAST_URI + city + "?" + queryParamApi + "=" + apiKey + "&" + queryParamLanguage + "=" + language;
    }

    @Override
    public String generateFiveDaysForecastUri(int city) {
        return FIVE_DAYS_FORECAST_URI + city + "?" + queryParamApi + "=" + apiKey + "&" + queryParamLanguage + "=" + language;
    }


}
