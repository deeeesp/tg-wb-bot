package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.SiteRequest;
import ru.stazaev.service.WeatherRequestService;

@Service
@Log4j2
public class WeatherRequestServiceImpl implements WeatherRequestService {
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final SiteRequest siteRequest;

    public WeatherRequestServiceImpl(ProducerService producerService, AppUserDAO appUserDAO, SiteRequest siteRequest) {
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
        this.siteRequest = siteRequest;
    }


    @Override
    public void dailyForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        var response = siteRequest.getDailyForecast(user.get().getCode());
//        var response = "{\"Headline\":{\"EffectiveDate\":\"2023-03-23T07:00:00+03:00\",\"EffectiveEpochDate\":1679544000,\"Severity\":5,\"Text\":\"Дождь Четверг\",\"Category\":\"rain\",\"EndDate\":\"2023-03-23T19:00:00+03:00\",\"EndEpochDate\":1679587200,\"MobileLink\":\"http://www.accuweather.com/ru/ru/voronezh/296543/daily-weather-forecast/296543\",\"Link\":\"http://www.accuweather.com/ru/ru/voronezh/296543/daily-weather-forecast/296543\"},\"DailyForecasts\":[{\"Date\":\"2023-03-19T07:00:00+03:00\",\"EpochDate\":1679198400,\"Temperature\":{\"Minimum\":{\"Value\":36.0,\"Unit\":\"F\",\"UnitType\":18},\"Maximum\":{\"Value\":38.0,\"Unit\":\"F\",\"UnitType\":18}},\"Day\":{\"Icon\":7,\"IconPhrase\":\"Облачно\",\"HasPrecipitation\":true,\"PrecipitationType\":\"Rain\",\"PrecipitationIntensity\":\"Light\"},\"Night\":{\"Icon\":7,\"IconPhrase\":\"Облачно\",\"HasPrecipitation\":false},\"Sources\":[\"AccuWeather\"],\"MobileLink\":\"http://www.accuweather.com/ru/ru/voronezh/296543/daily-weather-forecast/296543?day=1\",\"Link\":\"http://www.accuweather.com/ru/ru/voronezh/296543/daily-weather-forecast/296543?day=1\"}]}\n";
        System.out.println(response.body());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(jsonDailyFormatter(response.body()));
        producerService.produceAnswer(sendMessage);
    }

    @Override
    public void hourlyForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        var response = siteRequest.getHourlyForecast(user.get().getCode());
        System.out.println(response.body());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(jsonHourlyFormatter(response.body()));
        producerService.produceAnswer(sendMessage);
    }

    @Override
    public void verifyCity(AppUserDTO appUserDTO) {
        String city = appUserDTO.getCity();
        var code = 0;
        try {
            code = appUserDAO.findCodeByCity(city);
        } catch (Exception e) {
            log.error(e);
        }
        if (code == 0) {
            code = siteRequest.getCityCode(city);
            if (code == -1) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Ошибка обработки города. Проверьте название города.");
                sendMessage.setChatId(appUserDTO.getTelegramUserId());
                producerService.produceAnswer(sendMessage);
            }
        }
        appUserDTO.setCode(code);
        producerService.produceCodeResponse(appUserDTO);
    }

    public String jsonHourlyFormatter(String string){
        JSONArray array = new JSONArray(string);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            stringBuilder.append("Время - ").append(jsonObject.get("DateTime")).append("\n");
            stringBuilder.append("Погода - ").append(jsonObject.get("IconPhrase")).append("\n");
            stringBuilder.append("Ожидаются ли осадки - ").append(jsonObject.get("HasPrecipitation")).append("\n");
            stringBuilder.append("Возможность выпадения осадков - ").append(jsonObject.get("PrecipitationProbability")).append("\n");
            JSONObject temp = (JSONObject) jsonObject.get("Temperature");
            int temperature = temp.getInt("Value");
            System.out.println(temperature);
            stringBuilder.append("Температура - ").append(temperatureConvert(temperature));
            stringBuilder.append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("-----------------------------\n");
        }
        return String.valueOf(stringBuilder);
    }

    public String jsonDailyFormatter(String string){
        StringBuilder stringBuilder = new StringBuilder();
        JSONObject jsonObject = new JSONObject(string);
        JSONArray array = (JSONArray) jsonObject.get("DailyForecasts");
        for (int i = 0; i < array.length(); i++) {
            JSONObject forecast = array.getJSONObject(i);
            stringBuilder.append("Дата - ").append(forecast.get("Date")).append("\n");
            int min = forecast.getJSONObject("Temperature").getJSONObject("Minimum").getInt("Value");
            int max = forecast.getJSONObject("Temperature").getJSONObject("Maximum").getInt("Value");
            stringBuilder.append("Минимальная температура  - ").append(temperatureConvert(min)).append("\n");
            stringBuilder.append("Максимальная температура - ").append(temperatureConvert(max)).append("\n");
            stringBuilder.append("\n");
            stringBuilder.append("Погода днем - ").append(forecast.getJSONObject("Day").get("IconPhrase")).append("\n");
            stringBuilder.append("Осадки днем - ").append(forecast.getJSONObject("Day").get("HasPrecipitation")).append("\n");
            if(forecast.getJSONObject("Day").get("HasPrecipitation").equals(true)){
                stringBuilder.append("Тип осадков - ").append(forecast.getJSONObject("Day").get("PrecipitationType")).append("\n");
                stringBuilder.append("Сила осадков - ").append(forecast.getJSONObject("Day").get("PrecipitationIntensity")).append("\n");
            }
            stringBuilder.append("\n");
            stringBuilder.append("Погода ночью - ").append(forecast.getJSONObject("Night").get("IconPhrase")).append("\n");
            stringBuilder.append("Осадки ночью - ").append(forecast.getJSONObject("Night").get("HasPrecipitation")).append("\n");
            if(forecast.getJSONObject("Night").get("HasPrecipitation").equals(true)){
                stringBuilder.append("Тип осадков - ").append(forecast.getJSONObject("Night").get("PrecipitationType")).append("\n");
                stringBuilder.append("Сила осадков - ").append(forecast.getJSONObject("Night").get("PrecipitationIntensity")).append("\n");

            }
            stringBuilder.append("\n");
            stringBuilder.append("-----------------------------").append("\n");
        }
        return String.valueOf(stringBuilder);
    }

    private int temperatureConvert(int value){
        return (int) ((value - 32)/1.8);
    }
}
