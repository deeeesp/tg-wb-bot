package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface WeatherRequestService {
    void makeWeatherRequest(Update update);
}
