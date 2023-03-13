package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;

public interface ConsumerService {
    void makeWeatherRequest(Update update);

    void cityCode(AppUserDTO appUserDTO);
}
