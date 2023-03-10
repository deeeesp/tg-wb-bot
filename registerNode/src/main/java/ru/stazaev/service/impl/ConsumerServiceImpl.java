package ru.stazaev.service.impl;


import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.ProducerService;

import static ru.stazaev.queue.RabbitQueue.REGISTER_USER;

@Service
@Log4j2
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues = REGISTER_USER)
    public void registerUser(Update update) {
        log.debug("Register - " + update);
        SendMessage sendMessage = new SendMessage();
        var message = update.getMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("регистрация" + " mes from registerNode");
        producerService.produceAnswer(sendMessage);
    }
}
