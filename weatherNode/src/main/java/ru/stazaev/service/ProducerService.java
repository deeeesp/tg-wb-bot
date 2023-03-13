package ru.stazaev.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.stazaev.entity.dto.AppUserDTO;

public interface ProducerService {
    void produceAnswer(SendMessage sendMessage);
    void produceCodeResponse(AppUserDTO appUserDTO);
}
