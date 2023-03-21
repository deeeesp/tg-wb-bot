package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.dao.DailyForecastDAO;
import ru.stazaev.dao.HourlyForecastDAO;
import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.JsonFormatterService;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.SiteRequest;
import ru.stazaev.service.WeatherRequestService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class WeatherRequestServiceImpl implements WeatherRequestService {
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final SiteRequest siteRequest;
    private final JsonFormatterService jsonFormatterService;

    private final DailyForecastDAO dailyForecastDAO;
    private final HourlyForecastDAO hourlyForecastDAO;

    //TODO DTO для прогноза с осадками и без, иначе приложение упадет

    public WeatherRequestServiceImpl(ProducerService producerService, AppUserDAO appUserDAO, SiteRequest siteRequest, JsonFormatterService jsonFormatterService, DailyForecastDAO dailyForecastDAO, HourlyForecastDAO hourlyForecastDAO) {
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
        this.siteRequest = siteRequest;
        this.jsonFormatterService = jsonFormatterService;
        this.dailyForecastDAO = dailyForecastDAO;
        this.hourlyForecastDAO = hourlyForecastDAO;
    }

//TODO логи
//TODO 12 часов и 5 дней
//TODO DTO для осадков

    @Override
    public void dayForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        int code = user.get().getCode();
        DailyForecast forecast;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        try {
            Date now = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(new java.sql.Date(new Date().getTime())));
            var forecastFromDao = dailyForecastDAO.findDailyForecastByCodeAndDate(code, now);
            if (forecastFromDao.isPresent()) {
                forecast = forecastFromDao.get();
            } else {
                var response = siteRequest.getDaysForecast(code, 1);
                forecast = jsonFormatterService.jsonDayFormatter(response.body());
                forecast.setCode(code);
                dailyForecastDAO.save(forecast);
            }
            sendMessage.setText(forecast.toString());
        } catch (Exception e) {
            log.error(e);
            sendMessage.setText("Ошибка обработки времени");
        }
        producerService.produceAnswer(sendMessage);
    }

    @Override
    public void FiveDaysForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        int code = user.get().getCode();
        try {
            Date now = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(new java.sql.Date(new Date().getTime())));
            var forecastFromDao = dailyForecastDAO.getDailyForecast(code, now);
            if (forecastFromDao.size() == 5) {
                for (int i = 0; i < forecastFromDao.size(); i++) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(forecastFromDao.get(i).toString());
                    producerService.produceAnswer(sendMessage);
                }
            } else {
                dailyForecastDAO.deleteAllByCode(code);
                var response = siteRequest.getDaysForecast(code, 5);
                List<DailyForecast> forecast = jsonFormatterService.jsonDaysFormatter(response.body());
                for (int i = 0; i < forecast.size(); i++) {
                    forecast.get(i).setCode(code);
                    dailyForecastDAO.save(forecast.get(i));
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(forecast.get(i).toString());
                    producerService.produceAnswer(sendMessage);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Ошибка обработки времени");
            producerService.produceAnswer(sendMessage);
        }
    }

    @Override
    public void hourForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        int code = user.get().getCode();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        HourlyForecast forecast;
        var forecastFromDao = hourlyForecastDAO.getHourForecast(code, new Date());
        if (!forecastFromDao.isEmpty()) {
            forecast = forecastFromDao.get(0);
        } else {
            var response = siteRequest.getHoursForecast(code, 1);
            forecast = jsonFormatterService.jsonHourFormatter(response.body());
            forecast.setCode(code);
            hourlyForecastDAO.save(forecast);
        }
        sendMessage.setText(forecast.toString());
        producerService.produceAnswer(sendMessage);
    }

    @Override
    public void twelveHourForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        int code = user.get().getCode();
        var forecastFromDao = hourlyForecastDAO.getHourForecast(code, new Date());
        if (forecastFromDao.size() == 12) {
            for (int i = 0; i < forecastFromDao.size(); i++) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                HourlyForecast hourlyForecast = forecastFromDao.get(i);
                sendMessage.setText(hourlyForecast.toString());
                producerService.produceAnswer(sendMessage);
            }
        } else {
            hourlyForecastDAO.deleteAllByCode(code);
            var response = siteRequest.getHoursForecast(code, 12);
            List<HourlyForecast> tempForecast = jsonFormatterService.jsonHoursFormatter(response.body());
            for (int i = 0; i < tempForecast.size(); i++) {
                tempForecast.get(i).setCode(code);
                hourlyForecastDAO.save(tempForecast.get(i));
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(tempForecast.get(i).toString());
                producerService.produceAnswer(sendMessage);
            }
        }
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


}
