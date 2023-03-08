package ru.stazaev.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@RestController
public class WebHookController {

    private final UpdateController updateController;

    public WebHookController(UpdateController updateController) {
        this.updateController = updateController;
    }


    @RequestMapping(value = "/callback/bot", method = RequestMethod.POST)
    public ResponseEntity<?> onUpdateReceived(@RequestBody Update update) {
//        log.debug(update.getMessage());
        updateController.processUpdate(update);
        return ResponseEntity.ok().build();
    }

}

