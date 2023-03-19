package ru.stazaev.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.UpdateProducer;
import ru.stazaev.utils.MessageUtils;

import static ru.stazaev.queue.RabbitQueue.*;

@Component
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;

    private final UpdateProducer updateProducer;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.getMessage() != null) {
            distributeMessage(update);
        } else {
            log.error("Received unsupported message type " + update);
        }
    }

    private void distributeMessage(Update update) {
        if (update.getMessage().hasText()) {
            var messageText = update.getMessage().getText();
            if (update.getMessage().isCommand()) {
                switch (messageText) {
                    case "/register" -> registerUser(update);
//                    case "/weather" -> weatherRequest(update);
                    case "/hourly" -> hourlyRequest(update);
                    case "/daily" -> dailyRequest(update);
                    default -> setView(messageUtils.generateSendMessageWithText(update, "messageText"));
                }
            } else {
                messageHandler(update);
            }
        }
    }

    private void dailyRequest(Update update) {
        updateProducer.produce(DAILY_FORECAST, update);
    }

    private void hourlyRequest(Update update) {
        updateProducer.produce(HOURLY_FORECAST, update);
    }

    private void messageHandler(Update update) {
        updateProducer.produce(MESSAGE_HANDLER, update);
    }

    private void registerUser(Update update) {
        updateProducer.produce(REGISTER_USER, update);
    }


    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
}
