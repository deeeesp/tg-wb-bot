package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void makeWeatherRequest(Update update);
}
