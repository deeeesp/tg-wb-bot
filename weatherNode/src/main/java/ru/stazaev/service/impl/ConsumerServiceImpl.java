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
    @RabbitListener(queues = HOURLY_FORECAST)
    public void hourlyRequest(Update update) {
        weatherRequestService.hourlyForecast(update);
    }

    @Override
    @RabbitListener(queues = DAILY_FORECAST)
    public void dailyRequest(Update update) {
        weatherRequestService.dailyForecast(update);
    }

    @Override
    @RabbitListener(queues = CITY_CODE_REQUEST)
    public void cityCode(AppUserDTO appUserDTO) {
        System.out.println("city request");
        log.debug(appUserDTO);
        weatherRequestService.verifyCity(appUserDTO);
    }
}
