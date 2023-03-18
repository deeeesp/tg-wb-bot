package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.SiteRequest;
import ru.stazaev.service.WeatherRequestService;

@Service
@Log4j2
public class WeatherRequestServiceImpl implements WeatherRequestService {
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final SiteRequest siteRequest;
    public WeatherRequestServiceImpl(ProducerService producerService, AppUserDAO appUserDAO, SiteRequest siteRequest) {
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
        this.siteRequest = siteRequest;
    }

    @Override
    public void makeWeatherRequest(Update update) {
    }

    @Override
    public void verifyCity(AppUserDTO appUserDTO) {
        String city = appUserDTO.getCity();
        var code = 0;
        try{
            code = appUserDAO.findCodeByCity(city);
        }catch (Exception e){
            log.error(e);
        }
        if (code == 0){
            code = siteRequest.getCityCode(city);
            if (code == -1){
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Ошибка обработки города. Проверьте название города.");
                sendMessage.setChatId(appUserDTO.getTelegramUserId());
                producerService.produceAnswer(sendMessage);
            }
        }
        appUserDTO.setCode(code);
        producerService.produceCodeResponse(appUserDTO);
    }


}
