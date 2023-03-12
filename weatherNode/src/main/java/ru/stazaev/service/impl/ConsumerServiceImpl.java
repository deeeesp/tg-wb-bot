package ru.stazaev.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.WeatherRequestService;

import static ru.stazaev.queue.RabbitQueue.WEATHER_REQUEST;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final WeatherRequestService weatherRequestService;

    public ConsumerServiceImpl(WeatherRequestService weatherRequestService) {
        this.weatherRequestService = weatherRequestService;
    }

    @Override
    @RabbitListener(queues = WEATHER_REQUEST)
    public void makeWeatherRequest(Update update) {
        weatherRequestService.makeWeatherRequest(update);
    }
}
