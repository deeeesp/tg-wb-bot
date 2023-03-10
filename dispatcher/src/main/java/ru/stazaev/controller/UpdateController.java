package ru.stazaev.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.service.UpdateProducer;
import ru.stazaev.utils.MessageUtils;

import static ru.stazaev.queue.RabbitQueue.REGISTER_USER;

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

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if (update == null){
            log.error("Received update is null");
            return;
        }
        if (update.getMessage() != null){
            distributeMessage(update);
        }else {
            log.error("Received unsupported message type " + update);
        }
    }

    private void distributeMessage(Update update) {
        if (update.getMessage().hasText()){
            var messageText = update.getMessage().getText();
            switch (messageText){
                case "/register":
                    registerUser(update);
                    break;
                default:
                    setView(messageUtils.generateSendMessageWithText(update,"messageText"));
                    break;
            }
        }
    }

    private void registerUser(Update update) {
        updateProducer.produce(REGISTER_USER,update);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
}
