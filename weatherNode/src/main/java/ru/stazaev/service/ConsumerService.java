package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;

public interface ConsumerService {
    void hourlyRequest(Update update);
    void dailyRequest(Update update);

    void cityCode(AppUserDTO appUserDTO);
}
