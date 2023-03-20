package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;

import java.text.ParseException;

public interface WeatherRequestService {
    void dailyForecast(Update update);

    void hourlyForecast(Update update);

    void verifyCity(AppUserDTO appUserDTO);
}
