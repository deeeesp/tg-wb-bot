package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;
import ru.stazaev.service.JsonFormatterService;
import ru.stazaev.service.mapper.forecast.HourlyMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class JsonFormatterServiceImpl implements JsonFormatterService {

    @Override
    public HourlyForecast jsonHourFormatter(String string) {
        JSONArray array = new JSONArray(string);
        JSONObject jsonObject = array.getJSONObject(0);
        return convertHourly(array.getJSONObject(0));
    }

    @Override
    public List<HourlyForecast> jsonHoursFormatter(String string) {
        JSONArray array = new JSONArray(string);
        List<HourlyForecast> forecasts = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            forecasts.add(convertHourly(array.getJSONObject(i)));
        }
        return forecasts;
    }

    @Override
    public DailyForecast jsonDayFormatter(String string) {
        JSONObject jsonObject = new JSONObject(string);
        JSONArray array = (JSONArray) jsonObject.get("DailyForecasts");
        return convertDaily(array.getJSONObject(0));
    }

    @Override
    public List<DailyForecast> jsonDaysFormatter(String string) {
        JSONObject jsonObject = new JSONObject(string);
        JSONArray array = (JSONArray) jsonObject.get("DailyForecasts");
        List<DailyForecast> forecasts = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            forecasts.add(convertDaily(array.getJSONObject(i)));
        }
        return forecasts;
    }

    private HourlyForecast convertHourly(JSONObject jsonObject){
        JSONObject temp = (JSONObject) jsonObject.get("Temperature");
        int temperature = temp.getInt("Value");
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonObject.getString("DateTime"));
        } catch (Exception e) {
            log.error(e);
            date = new Date();
        }
        if (jsonObject.getBoolean("HasPrecipitation")) {
            return HourlyForecast.builder()
                    .date(date)
                    .weather(jsonObject.getString("IconPhrase"))
                    .temperature(temperatureConvert(temperature))
                    .hasPrecipitation(jsonObject.getBoolean("HasPrecipitation"))
                    .precipitationProbability(jsonObject.getInt("PrecipitationProbability"))
                    .precipitationType(jsonObject.getString("PrecipitationType"))
                    .precipitationIntensity(jsonObject.getString("PrecipitationIntensity"))
                    .build();
        } else {
            return HourlyForecast.builder()
                    .date(date)
                    .weather(jsonObject.getString("IconPhrase"))
                    .temperature(temperatureConvert(temperature))
                    .hasPrecipitation(jsonObject.getBoolean("HasPrecipitation"))
                    .precipitationProbability(jsonObject.getInt("PrecipitationProbability"))
                    .build();
        }
    }

    private DailyForecast convertDaily(JSONObject forecast){
        int min = forecast.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
        int max = forecast.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
        JSONObject day = forecast.getJSONObject("Day");
        JSONObject night = forecast.getJSONObject("Night");
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(forecast.getString("Date"));
        } catch (Exception e) {
            log.error(e);
            date = new Date();
        }
        boolean hasDayPrecipitation = day.getBoolean("HasPrecipitation");
        boolean hasNightPrecipitation = night.getBoolean("HasPrecipitation");
        DailyForecast dailyForecast = DailyForecast.builder()
                .date(date)
                .maxTemperature(temperatureConvert(max))
                .minTemperature(temperatureConvert(min))
                .dayWeather(day.getString("IconPhrase"))
                .dayHasPrecipitation(day.getBoolean("HasPrecipitation"))
                .nightWeather(night.getString("IconPhrase"))
                .nightHasPrecipitation(night.getBoolean("HasPrecipitation"))
                .build();
        if (hasDayPrecipitation){
            dailyForecast.setDayPrecipitationType(day.getString("PrecipitationType"));
            dailyForecast.setDayPrecipitationIntensity(day.getString("PrecipitationIntensity"));
        }
        if (hasNightPrecipitation){
            dailyForecast.setNightPrecipitationType(night.getString("PrecipitationType"));
            dailyForecast.setNightPrecipitationIntensity(night.getString("PrecipitationIntensity"));
        }
        return dailyForecast;
    }

    private int temperatureConvert(int value) {
        return (int) ((value - 32) / 1.8);
    }
}
