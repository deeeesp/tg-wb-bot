package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ProducerService;

import static ru.stazaev.queue.RabbitQueue.ANSWER_MESSAGE;
import static ru.stazaev.queue.RabbitQueue.CITY_CODE_RESPONSE;

@Service
@Log4j2
public class ProducerServiceImpl implements ProducerService {
    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void produceCodeResponse(AppUserDTO appUserDTO) {
        rabbitTemplate.convertAndSend(CITY_CODE_RESPONSE,appUserDTO);
    }
}
