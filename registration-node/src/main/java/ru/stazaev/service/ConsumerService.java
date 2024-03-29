package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;

public interface ConsumerService {
    void registerUser(Update update);
    void consumeTextMessageUpdates(Update update);
    void saveUserCityCode(AppUserDTO appUserDTO);

}
