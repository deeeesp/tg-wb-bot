package ru.stazaev.service.impl;


import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.RegistrationService;

import static ru.stazaev.queue.RabbitQueue.MESSAGE_HANDLER;
import static ru.stazaev.queue.RabbitQueue.REGISTER_USER;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {

    private final RegistrationService registrationService;
    private final ProducerService producerService;

    public ConsumerServiceImpl(RegistrationService registrationService, ProducerService producerService) {
        this.registrationService = registrationService;
        this.producerService = producerService;
    }


    @Override
    @RabbitListener(queues = REGISTER_USER)
    public void registerUser(Update update) {
        log.debug("Register - " + update);
        registrationService.registerOrGetUser(update);
    }

    @Override
    @RabbitListener(queues = MESSAGE_HANDLER)
    public void consumeTextMessageUpdates(Update update) {
        //TODO нормальный update
        var message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
//        var user = registrationService.registerOrGetUser(update);
        registrationService.setCity(update);
        sendMessage.setText("обработка сообщения + " + message.getText());
        sendMessage.setChatId(message.getChatId());
        producerService.produceAnswer(sendMessage);
    }
}
