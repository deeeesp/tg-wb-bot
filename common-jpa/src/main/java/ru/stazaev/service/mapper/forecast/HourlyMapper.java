package ru.stazaev.service.mapper.forecast;

import org.springframework.stereotype.Service;
import ru.stazaev.entity.HourlyForecast;
import ru.stazaev.entity.dto.forecast.HourlyWithPrecipitationDTO;
import ru.stazaev.entity.dto.forecast.HourlyWoutPrecipitationDTO;

@Service
public class HourlyMapper {
    public HourlyForecast withPrecipitationDTOToEntity(HourlyWithPrecipitationDTO withPrecipitationDTO){
        return HourlyForecast.builder()
                .date(withPrecipitationDTO.getDate())
                .weather(withPrecipitationDTO.getWeather())
                .temperature(withPrecipitationDTO.getTemperature())
                .hasPrecipitation(withPrecipitationDTO.isHasPrecipitation())
                .precipitationProbability(withPrecipitationDTO.getPrecipitationProbability())
                .precipitationType(withPrecipitationDTO.getPrecipitationType())
                .precipitationIntensity(withPrecipitationDTO.getPrecipitationIntensity())
                .build();
    }

    public HourlyForecast withoutPrecipitationDTOToEntity(HourlyWoutPrecipitationDTO withoutPrecipitationDTO){
        return HourlyForecast.builder()
                .date(withoutPrecipitationDTO.getDate())
                .weather(withoutPrecipitationDTO.getWeather())
                .temperature(withoutPrecipitationDTO.getTemperature())
                .hasPrecipitation(withoutPrecipitationDTO.isHasPrecipitation())
                .precipitationProbability(withoutPrecipitationDTO.getPrecipitationProbability())
                .build();
    }

    public HourlyWithPrecipitationDTO entityToWithPrecipitationDTO(HourlyForecast hourlyForecast){
        return HourlyWithPrecipitationDTO.builder()
                .date(hourlyForecast.getDate())
                .weather(hourlyForecast.getWeather())
                .temperature(hourlyForecast.getTemperature())
                .hasPrecipitation(hourlyForecast.isHasPrecipitation())
                .precipitationIntensity(hourlyForecast.getPrecipitationIntensity())
                .precipitationType(hourlyForecast.getPrecipitationType())
                .precipitationIntensity(hourlyForecast.getPrecipitationIntensity())
                .build();
    }

    public HourlyWoutPrecipitationDTO entityToWithoutPrecipitationDTO(HourlyForecast hourlyForecast){
        return HourlyWoutPrecipitationDTO.builder()
                .date(hourlyForecast.getDate())
                .weather(hourlyForecast.getWeather())
                .temperature(hourlyForecast.getTemperature())
                .hasPrecipitation(hourlyForecast.isHasPrecipitation())
                .build();
    }



}
