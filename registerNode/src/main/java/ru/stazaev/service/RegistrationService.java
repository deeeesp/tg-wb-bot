package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface RegistrationService {
    void registerUser(Update update);
}
