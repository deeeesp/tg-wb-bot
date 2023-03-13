package ru.stazaev.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.stazaev.dao.AppUserDAO;
import ru.stazaev.entity.dto.AppUserDTO;
import ru.stazaev.service.ProducerService;
import ru.stazaev.service.WeatherRequestService;

@Service
public class WeatherRequestServiceImpl implements WeatherRequestService {
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    public WeatherRequestServiceImpl(ProducerService producerService, AppUserDAO appUserDAO) {
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void makeWeatherRequest(Update update) {
    }

    @Override
    public void verifyCity(AppUserDTO appUserDTO) {
        String city = appUserDTO.getCity();
//        var userByCity = appUserDAO.findAppUserByCity(city);
//        if (userByCity.isEmpty()){
//            TODO запрос на сайт
//        }else {
//            appUserDTO.setCode(userByCity.get().getCode());
//        }
        System.out.println("tg id" + appUserDTO.getTelegramUserId());
        System.out.println("city name" + appUserDTO.getCity());
        System.out.println("обработка города. пока по стандарту сетится код воронежа - 296543");
//        appUserDTO.setCode(296543);
        producerService.produceCodeResponse(appUserDTO);
    }


}
