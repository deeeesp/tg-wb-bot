package ru.stazaev.service.impl;


import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.RegistrationService;

import static ru.stazaev.queue.RabbitQueue.REGISTER_USER;

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
        log.debug("Register - " + update);
        registrationService.registerUser(update);
    }
}
