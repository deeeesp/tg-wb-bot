package ru.stazaev.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.entity.AppUser;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.entity.enums.UserState;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.RegistrationService;

import static ru.stazaev.entity.enums.UserState.BASIC_STATE;
import static ru.stazaev.entity.enums.UserState.WAIT_FOR_CITY_STATE;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final AppUserDAO appUserDAO;
    private final ProducerService producerService;

    public RegistrationServiceImpl(AppUserDAO appUserDAO, ProducerService producerService) {
        this.appUserDAO = appUserDAO;
        this.producerService = producerService;
    }

    @Override
    public AppUser registerOrGetUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        var optional = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
        if (optional.isEmpty()) {
            AppUser appUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .name(telegramUser.getFirstName())
                    .state(WAIT_FOR_CITY_STATE)
                    .build();


            producerService.produceAnswer("Введите название города", update.getMessage().getChatId());
            return appUserDAO.save(appUser);
        }
        return optional.get();
    }

    public void verifyIntroducedCity(Update update) {
        var user = registerOrGetUser(update);
        var message = update.getMessage();
        if (user.getState().equals(UserState.WAIT_FOR_CITY_STATE)) {
            String introducedCity = message.getText();
            var userDTO = AppUserDTO.builder()
                    .city(introducedCity)
                    .telegramUserId(user.getTelegramUserId())
                    .build();
            producerService.produceCodeRequest(userDTO);
        }
    }


    @Override
    public void saveCode(AppUserDTO appUserDTO) {
        long userTelegramId = appUserDTO.getTelegramUserId();
        int code = appUserDTO.getCode();
        String city = appUserDTO.getCity();
        appUserDAO.updateCityCopeByTelegramUserId(userTelegramId, code, city, BASIC_STATE);
        SendMessage sendMessage = new SendMessage();
        producerService.produceAnswer("Код города сохранен", userTelegramId);
    }
}
