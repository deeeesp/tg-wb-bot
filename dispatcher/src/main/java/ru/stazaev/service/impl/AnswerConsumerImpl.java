package ru.stazaev.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.stazaev.controller.UpdateController;
import ru.stazaev.service.AnswerConsumer;

import static ru.stazaev.queue.RabbitQueue.ANSWER_MESSAGE;

public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
