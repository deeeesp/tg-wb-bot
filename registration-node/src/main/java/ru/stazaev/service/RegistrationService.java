package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.AppUser;
import ru.stazaev.entity.dto.AppUserDTO;

public interface RegistrationService {
    AppUser registerOrGetUser(Update update);

    void saveCode(AppUserDTO appUserDTO);

    void verifyIntroducedCity(Update update);
}
