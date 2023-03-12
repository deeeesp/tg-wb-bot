package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.AppUser;

public interface RegistrationService {
    AppUser registerOrGetUser(Update update);

    void setCity(Update update);
}
