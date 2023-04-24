package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.dao.DailyForecastDAO;
import ru.stazaev.dao.HourlyForecastDAO;
import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.HourlyForecast;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.entity.dto.forecast.Forecast;
import ru.stazaev.service.JsonFormatterService;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.SiteRequest;
import ru.stazaev.service.WeatherRequestService;
import ru.stazaev.service.mapper.forecast.DailyMapper;
import ru.stazaev.service.mapper.forecast.HourlyMapper;

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
    private final HourlyMapper hourlyMapper;
    private final DailyMapper dailyMapper;
    private final DailyForecastDAO dailyForecastDAO;
    private final HourlyForecastDAO hourlyForecastDAO;


    public WeatherRequestServiceImpl(ProducerService producerService, AppUserDAO appUserDAO, SiteRequest siteRequest, JsonFormatterService jsonFormatterService, HourlyMapper hourlyMapper, DailyMapper dailyMapper, DailyForecastDAO dailyForecastDAO, HourlyForecastDAO hourlyForecastDAO) {
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
        this.siteRequest = siteRequest;
        this.jsonFormatterService = jsonFormatterService;
        this.hourlyMapper = hourlyMapper;
        this.dailyMapper = dailyMapper;
        this.dailyForecastDAO = dailyForecastDAO;
        this.hourlyForecastDAO = hourlyForecastDAO;
    }


    @Override
    public void dayForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        if (user.isPresent()) {
            int code = user.get().getCode();
            DailyForecast forecast;
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
                var forecastDTO = dailyForecastToDTO(forecast);
                producerService.produceAnswer(forecastDTO.toString(), message.getChatId());
            } catch (Exception e) {
                log.error(e);
                String sendMessage = "Ошибка обработки времени";
                producerService.produceAnswer(sendMessage, message.getChatId());

            }
        } else {
            sendMesError(update);
        }
    }

    @Override
    public void FiveDaysForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        var chatId = message.getChatId();
        if (user.isPresent()) {
            int code = user.get().getCode();
            try {
                Date now = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(new java.sql.Date(new Date().getTime())));
                var forecastFromDao = dailyForecastDAO.getDailyForecast(code, now);
                if (forecastFromDao.size() == 5) {
                    for (DailyForecast dailyForecast : forecastFromDao) {
                        var forecastDTO = dailyForecastToDTO(dailyForecast);
                        producerService.produceAnswer(forecastDTO.toString(), chatId);
                    }
                } else {
                    dailyForecastDAO.deleteAllByCode(code);
                    var response = siteRequest.getDaysForecast(code, 5);
                    List<DailyForecast> forecast = jsonFormatterService.jsonDaysFormatter(response.body());
                    for (DailyForecast dailyForecast : forecast) {
                        dailyForecast.setCode(code);
                        dailyForecastDAO.save(dailyForecast);
                        var forecastDTO = dailyForecastToDTO(dailyForecast);
                        producerService.produceAnswer(forecastDTO.toString(), chatId);
                    }
                }
            } catch (Exception e) {
                String error = "Ошибка обработки времени";
                producerService.produceAnswer(error, chatId);
            }
        } else {
            sendMesError(update);
        }
    }

    @Override
    public void hourForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        var chatId = message.getChatId();
        if (user.isPresent()) {
            int code = user.get().getCode();
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
            var forecastResult = hourlyForecastToDTO(forecast);
            producerService.produceAnswer(forecastResult.toString(), chatId);
        } else {
            sendMesError(update);
        }
    }

    @Override
    public void twelveHourForecast(Update update) {
        var message = update.getMessage();
        var user = appUserDAO.findAppUserByTelegramUserId(message.getChatId());
        var chatId = message.getChatId();
        if (user.isPresent()) {
            int code = user.get().getCode();
            var forecastFromDao = hourlyForecastDAO.getHourForecast(code, new Date());
            if (forecastFromDao.size() == 12) {
                for (HourlyForecast forecast : forecastFromDao) {
                    var forecastResult = hourlyForecastToDTO(forecast);
                    producerService.produceAnswer(forecastResult.toString(), chatId);
                }
            } else {
                hourlyForecastDAO.deleteAllByCode(code);
                var response = siteRequest.getHoursForecast(code, 12);
                List<HourlyForecast> tempForecast = jsonFormatterService.jsonHoursFormatter(response.body());
                for (HourlyForecast hourlyForecast : tempForecast) {
                    hourlyForecast.setCode(code);
                    hourlyForecastDAO.save(hourlyForecast);
                    var forecastDTO = hourlyForecastToDTO(hourlyForecast);
                    producerService.produceAnswer(forecastDTO.toString(), chatId);
                }
            }
        } else {
            sendMesError(update);
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
            var response = siteRequest.getCityCode(city).body();
            code = jsonFormatterService.jsonCityCode(response);
            if (code == -1) {
                String mesError = "Город с названием " + appUserDTO.getCity() + ". Не найден.\n" +
                        "Введите название города еще раз.";
                producerService.produceAnswer(mesError, appUserDTO.getTelegramUserId());
                return;
            }
        }
        appUserDTO.setCode(code);
        producerService.produceCodeResponse(appUserDTO);
    }

    public Forecast dailyForecastToDTO(DailyForecast dailyForecast) {
        if (dailyForecast.isDayHasPrecipitation() && dailyForecast.isNightHasPrecipitation()) {
            return dailyMapper.EntityToDTOWithPrecipitation(dailyForecast);
        } else if (dailyForecast.isDayHasPrecipitation()) {
            return dailyMapper.EntityToDTOWithDayPrecipitation(dailyForecast);
        } else if (dailyForecast.isNightHasPrecipitation()) {
            return dailyMapper.EntityToDTOWithNightPrecipitation(dailyForecast);
        }
        return dailyMapper.EntityToDTOWoutPrecipitation(dailyForecast);
    }

    public Forecast hourlyForecastToDTO(HourlyForecast hourlyForecast) {
        if (hourlyForecast.isHasPrecipitation()) {
            return hourlyMapper.entityToWithPrecipitationDTO(hourlyForecast);
        }
        return hourlyMapper.entityToWithoutPrecipitationDTO(hourlyForecast);
    }

    private void sendMesError(Update update) {
        String mesError = "Чтобы пользоваться ботом, зарегистрируйтесь, введя /register";
        producerService.produceAnswer(mesError, update.getMessage().getChatId());
    }
}
