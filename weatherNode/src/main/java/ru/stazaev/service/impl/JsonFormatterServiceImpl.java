package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;
import ru.stazaev.service.JsonFormatterService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Log4j2
public class JsonFormatterServiceImpl implements JsonFormatterService {
    @Override
    public HourlyForecast jsonHourlyFormatter(String string) {
        JSONArray array = new JSONArray(string);
        JSONObject jsonObject = array.getJSONObject(0);
        JSONObject temp = (JSONObject) jsonObject.get("Temperature");
        int temperature = temp.getInt("Value");
        //TODO обработка нескольких записей
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonObject.getString("DateTime"));
        }catch (Exception e){
            log.error(e);
            date = new Date();
        }

//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < array.length(); i++) {
//        stringBuilder.append("Время - ").append(jsonObject.get("DateTime")).append("\n");
//        stringBuilder.append("Погода - ").append(jsonObject.get("IconPhrase")).append("\n");
//        stringBuilder.append("Ожидаются ли осадки - ").append(jsonObject.get("HasPrecipitation")).append("\n");
//        stringBuilder.append("Возможность выпадения осадков - ").append(jsonObject.get("PrecipitationProbability")).append("\n");
//            System.out.println(temperature);
//            stringBuilder.append("Температура - ").append(temperatureConvert(temperature));
//            stringBuilder.append("\n");
//            stringBuilder.append("\n");
//            stringBuilder.append("-----------------------------\n");
//        }
        if (jsonObject.getBoolean("HasPrecipitation")){
            return HourlyForecast.builder()
                    .date(date)
                    .weather(jsonObject.getString("IconPhrase"))
                    .temperature(temperatureConvert(temperature))
                    .hasPrecipitation(jsonObject.getBoolean("HasPrecipitation"))
                    .precipitationProbability(jsonObject.getInt("PrecipitationProbability"))
                    .precipitationType(jsonObject.getString("PrecipitationType"))
                    .precipitationIntensity(jsonObject.getString("PrecipitationIntensity"))
                    .build();
        }else {
            return HourlyForecast.builder()
                    .date(date)
                    .weather(jsonObject.getString("IconPhrase"))
                    .temperature(temperatureConvert(temperature))
                    .hasPrecipitation(jsonObject.getBoolean("HasPrecipitation"))
                    .precipitationProbability(jsonObject.getInt("PrecipitationProbability"))
//                    .precipitationType(jsonObject.getString("PrecipitationType"))
//                    .precipitationIntensity(jsonObject.getString("PrecipitationIntensity"))
                    .build();
        }
    }

    @Override
    public DailyForecast jsonDailyFormatter(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject jsonObject = new JSONObject(string);
        JSONArray array = (JSONArray) jsonObject.get("DailyForecasts");
        JSONObject forecast = array.getJSONObject(0);
        int min = forecast.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
        int max = forecast.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
        JSONObject day = forecast.getJSONObject("Day");
        JSONObject night = forecast.getJSONObject("Night");
        //TODO DTO для прогнозов с осадками
        //TODO обработка нескольких записей
//        for (int i = 0; i < array.length(); i++) {
//        stringBuilder.append("Дата - ").append(forecast.get("Date")).append("\n");
//            stringBuilder.append("Минимальная температура  - ").append(temperatureConvert(min)).append("\n");
//            stringBuilder.append("Максимальная температура - ").append(temperatureConvert(max)).append("\n");
//            stringBuilder.append("\n");
//            stringBuilder.append("Погода днем - ").append(forecast.getJSONObject("Day").get("IconPhrase")).append("\n");
//            stringBuilder.append("Осадки днем - ").append(forecast.getJSONObject("Day").get("HasPrecipitation")).append("\n");
//            if (forecast.getJSONObject("Day").get("HasPrecipitation").equals(true)) {
//                stringBuilder.append("Тип осадков - ").append(forecast.getJSONObject("Day").get("PrecipitationType")).append("\n");
//                stringBuilder.append("Сила осадков - ").append(forecast.getJSONObject("Day").get("PrecipitationIntensity")).append("\n");
//            }
//            stringBuilder.append("\n");
//            stringBuilder.append("Погода ночью - ").append(forecast.getJSONObject("Night").get("IconPhrase")).append("\n");
//            stringBuilder.append("Осадки ночью - ").append(forecast.getJSONObject("Night").get("HasPrecipitation")).append("\n");
//            if (forecast.getJSONObject("Night").get("HasPrecipitation").equals(true)) {
//                stringBuilder.append("Тип осадков - ").append(forecast.getJSONObject("Night").get("PrecipitationType")).append("\n");
//                stringBuilder.append("Сила осадков - ").append(forecast.getJSONObject("Night").get("PrecipitationIntensity")).append("\n");
//
//            }
//        }
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(forecast.getString("Date"));
        }catch (Exception e){
            log.error(e);
            date = new Date();
        }

        return DailyForecast.builder()
                .date(date)
                .maxTemperature(temperatureConvert(max))
                .minTemperature(temperatureConvert(min))
                .dayWeather(day.getString("IconPhrase"))
                .dayHasPrecipitation(day.getBoolean("HasPrecipitation"))
                .nightWeather(night.getString("IconPhrase"))
                .nightHasPrecipitation(night.getBoolean("HasPrecipitation"))
                .build();
    }

    private int temperatureConvert(int value) {
        return (int) ((value - 32) / 1.8);
    }
}
