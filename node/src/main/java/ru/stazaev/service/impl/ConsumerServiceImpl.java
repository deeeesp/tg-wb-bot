package ru.stazaev.service.impl;


import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.RegistrationService;

import static ru.stazaev.queue.RabbitQueue.*;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {

    private final RegistrationService registrationService;

    public ConsumerServiceImpl(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @Override
    @RabbitListener(queues = REGISTER_USER)
    public void registerUser(Update update) {
        log.debug(update.getMessage().getText());
        log.debug("Register - " + update);
        registrationService.registerOrGetUser(update);
    }

    @Override
    @RabbitListener(queues = MESSAGE_HANDLER)
    public void consumeTextMessageUpdates(Update update) {
        log.debug(update.getMessage().getText());
        System.out.println("mes handler");
        registrationService.verifyIntroducedCity(update);
    }

    @Override
    @RabbitListener(queues = CITY_CODE_RESPONSE)
    public void saveUserCityCode(AppUserDTO appUserDTO){
        log.debug(appUserDTO);
        registrationService.saveCode(appUserDTO);
    }
}
