package ru.stazaev.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.WeatherRequestService;

@Service
public class WeatherRequestServiceImpl implements WeatherRequestService {
    private final ProducerService producerService;
//C3iBJ1zJ0upNcO8h76JFXGy8PopWv3Gl
    public WeatherRequestServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void makeWeatherRequest(Update update) {

    }
}
