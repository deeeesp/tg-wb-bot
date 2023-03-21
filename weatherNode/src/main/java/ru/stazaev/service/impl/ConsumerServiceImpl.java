package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.WeatherRequestService;

import static ru.stazaev.queue.RabbitQueue.*;

@Service
@Log4j2
public class ConsumerServiceImpl implements ConsumerService {

    private final WeatherRequestService weatherRequestService;

    public ConsumerServiceImpl(WeatherRequestService weatherRequestService) {
        this.weatherRequestService = weatherRequestService;
    }

    @Override
    @RabbitListener(queues = HOUR_FORECAST)
    public void hourlyRequest(Update update) {
        log.debug(update);
        weatherRequestService.hourForecast(update);
    }

    @Override
    @RabbitListener(queues = DAY_FORECAST)
    public void dailyRequest(Update update) {
        log.debug(update);
        weatherRequestService.dayForecast(update);
    }

    @Override
    @RabbitListener(queues = TWELVE_HOURS_FORECAST)
    public void twelveHoursRequest(Update update) {
        log.debug(update);
        weatherRequestService.twelveHourForecast(update);
    }

    @Override
    @RabbitListener(queues = FIVE_DAYS_FORECAST)
    public void fiveDaysRequest(Update update) {
        log.debug(update);
        weatherRequestService.FiveDaysForecast(update);
    }

    @Override
    @RabbitListener(queues = CITY_CODE_REQUEST)
    public void cityCode(AppUserDTO appUserDTO) {
        log.debug(appUserDTO);
        weatherRequestService.verifyCity(appUserDTO);
    }
}
