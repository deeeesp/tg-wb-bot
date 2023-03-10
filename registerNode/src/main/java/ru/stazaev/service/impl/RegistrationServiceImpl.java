package ru.stazaev.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.entity.AppUser;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final AppUserDAO appUserDAO;
    private final ProducerService producerService;

    public RegistrationServiceImpl(AppUserDAO appUserDAO, ProducerService producerService) {
        this.appUserDAO = appUserDAO;
        this.producerService = producerService;
    }

    @Override
    public void registerUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        var optional = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
        if (optional.isEmpty()){
            AppUser appUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .name(telegramUser.getFirstName())
                    //TODO добавить город и подумать с именем
                    .build();
            appUserDAO.save(appUser);
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Вы были зарегистрированы");
        sendMessage.setChatId(update.getMessage().getChatId());
        producerService.produceAnswer(sendMessage);
    }
}
