package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;

public interface WeatherRequestService {
    void dayForecast(Update update);

    void hourForecast(Update update);

    void verifyCity(AppUserDTO appUserDTO);

    void twelveHourForecast(Update update);

    void FiveDaysForecast(Update update);
}
