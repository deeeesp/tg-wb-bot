package ru.stazaev.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ProducerService;

import static ru.stazaev.queue.RabbitQueue.ANSWER_MESSAGE;
import static ru.stazaev.queue.RabbitQueue.CITY_CODE_REQUEST;


@Service
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    public void produceCodeRequest(AppUserDTO appUserDTO){
        rabbitTemplate.convertAndSend(CITY_CODE_REQUEST,appUserDTO);
    }
}
