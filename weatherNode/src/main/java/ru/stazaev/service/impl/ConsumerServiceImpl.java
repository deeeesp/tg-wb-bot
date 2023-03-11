package ru.stazaev.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.ConsumerService;
import ru.stazaev.service.ProducerService;

import static ru.stazaev.queue.RabbitQueue.WEATHER_REQUEST;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;

    public ConsumerServiceImpl(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    @RabbitListener(queues = WEATHER_REQUEST)
    public void makeWeatherRequest(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("make weather Request");
        producerService.produceAnswer(sendMessage);
    }
}
