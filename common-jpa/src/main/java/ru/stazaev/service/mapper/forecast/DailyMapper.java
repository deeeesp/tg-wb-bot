package ru.stazaev.service.mapper.forecast;

import org.springframework.stereotype.Service;
import ru.stazaev.entity.DailyForecast;
import ru.stazaev.entity.dto.forecast.DailyWithDayPrecipitationDTO;
import ru.stazaev.entity.dto.forecast.DailyWithNightPrecipitationDTO;
import ru.stazaev.entity.dto.forecast.DailyWithPrecipitationDTO;
import ru.stazaev.entity.dto.forecast.DailyWoutPrecipitationDTO;

@Service
public class DailyMapper {
    public DailyForecast withPrecipitationDTOToEntity(DailyWithPrecipitationDTO dtoToEntity){
        return DailyForecast.builder()
                .date(dtoToEntity.getDate())
                .maxTemperature(dtoToEntity.getMaxTemperature())
                .minTemperature(dtoToEntity.getMinTemperature())
                .dayWeather(dtoToEntity.getDayWeather())
                .dayHasPrecipitation(dtoToEntity.isDayHasPrecipitation())
                .dayPrecipitationType(dtoToEntity.getDayPrecipitationType())
                .dayPrecipitationIntensity(dtoToEntity.getDayPrecipitationIntensity())
                .nightWeather(dtoToEntity.getNightWeather())
                .nightHasPrecipitation(dtoToEntity.isNightHasPrecipitation())
                .nightPrecipitationType(dtoToEntity.getNightPrecipitationType())
                .nightPrecipitationIntensity(dtoToEntity.getNightPrecipitationIntensity())
                .build();
    }

    public DailyForecast withDayPrecipitationDTOToEntity(DailyWithDayPrecipitationDTO dtoToEntity){
        return DailyForecast.builder()
                .date(dtoToEntity.getDate())
                .maxTemperature(dtoToEntity.getMaxTemperature())
                .minTemperature(dtoToEntity.getMinTemperature())
                .dayWeather(dtoToEntity.getDayWeather())
                .dayHasPrecipitation(dtoToEntity.isDayHasPrecipitation())
                .dayPrecipitationType(dtoToEntity.getDayPrecipitationType())
                .dayPrecipitationIntensity(dtoToEntity.getDayPrecipitationIntensity())
                .nightWeather(dtoToEntity.getNightWeather())
                .nightHasPrecipitation(dtoToEntity.isNightHasPrecipitation())
                .build();
    }

    public DailyForecast withNightPrecipitationDTOToEntity(DailyWithNightPrecipitationDTO dtoToEntity){
        return DailyForecast.builder()
                .date(dtoToEntity.getDate())
                .maxTemperature(dtoToEntity.getMaxTemperature())
                .minTemperature(dtoToEntity.getMinTemperature())
                .dayWeather(dtoToEntity.getDayWeather())
                .dayHasPrecipitation(dtoToEntity.isDayHasPrecipitation())
                .nightWeather(dtoToEntity.getNightWeather())
                .nightHasPrecipitation(dtoToEntity.isNightHasPrecipitation())
                .nightPrecipitationType(dtoToEntity.getNightPrecipitationType())
                .nightPrecipitationIntensity(dtoToEntity.getNightPrecipitationIntensity())
                .build();
    }

    public DailyForecast woutPrecipitationDTOToEntity(DailyWoutPrecipitationDTO dtoToEntity){
        return DailyForecast.builder()
                .date(dtoToEntity.getDate())
                .maxTemperature(dtoToEntity.getMaxTemperature())
                .minTemperature(dtoToEntity.getMinTemperature())
                .dayWeather(dtoToEntity.getDayWeather())
                .dayHasPrecipitation(dtoToEntity.isDayHasPrecipitation())
                .nightWeather(dtoToEntity.getNightWeather())
                .nightHasPrecipitation(dtoToEntity.isNightHasPrecipitation())
                .build();
    }

    public DailyWithPrecipitationDTO EntityToDTOWithPrecipitation(DailyForecast dailyForecast){
        return DailyWithPrecipitationDTO.builder()
                .date(dailyForecast.getDate())
                .maxTemperature(dailyForecast.getMaxTemperature())
                .minTemperature(dailyForecast.getMinTemperature())
                .dayWeather(dailyForecast.getDayWeather())
                .dayHasPrecipitation(dailyForecast.isDayHasPrecipitation())
                .dayPrecipitationType(dailyForecast.getDayPrecipitationType())
                .dayPrecipitationIntensity(dailyForecast.getDayPrecipitationIntensity())
                .nightWeather(dailyForecast.getNightWeather())
                .nightHasPrecipitation(dailyForecast.isNightHasPrecipitation())
                .nightPrecipitationType(dailyForecast.getNightPrecipitationType())
                .nightPrecipitationIntensity(dailyForecast.getNightPrecipitationIntensity())
                .build();
    }

    public DailyWithDayPrecipitationDTO EntityToDTOWithDayPrecipitation(DailyForecast dailyForecast){
        return DailyWithDayPrecipitationDTO.builder()
                .date(dailyForecast.getDate())
                .maxTemperature(dailyForecast.getMaxTemperature())
                .minTemperature(dailyForecast.getMinTemperature())
                .dayWeather(dailyForecast.getDayWeather())
                .dayHasPrecipitation(dailyForecast.isDayHasPrecipitation())
                .dayPrecipitationType(dailyForecast.getDayPrecipitationType())
                .dayPrecipitationIntensity(dailyForecast.getDayPrecipitationIntensity())
                .nightWeather(dailyForecast.getNightWeather())
                .nightHasPrecipitation(dailyForecast.isNightHasPrecipitation())
                .build();
    }

    public DailyWithNightPrecipitationDTO EntityToDTOWithNightPrecipitation(DailyForecast dailyForecast){
        return DailyWithNightPrecipitationDTO.builder()
                .date(dailyForecast.getDate())
                .maxTemperature(dailyForecast.getMaxTemperature())
                .minTemperature(dailyForecast.getMinTemperature())
                .dayWeather(dailyForecast.getDayWeather())
                .dayHasPrecipitation(dailyForecast.isDayHasPrecipitation())
                .nightWeather(dailyForecast.getNightWeather())
                .nightHasPrecipitation(dailyForecast.isNightHasPrecipitation())
                .nightPrecipitationType(dailyForecast.getNightPrecipitationType())
                .nightPrecipitationIntensity(dailyForecast.getNightPrecipitationIntensity())
                .build();
    }

    public DailyWoutPrecipitationDTO EntityToDTOWoutPrecipitation(DailyForecast dailyForecast){
        return DailyWoutPrecipitationDTO.builder()
                .date(dailyForecast.getDate())
                .maxTemperature(dailyForecast.getMaxTemperature())
                .minTemperature(dailyForecast.getMinTemperature())
                .dayWeather(dailyForecast.getDayWeather())
                .dayHasPrecipitation(dailyForecast.isDayHasPrecipitation())
                .nightWeather(dailyForecast.getNightWeather())
                .nightHasPrecipitation(dailyForecast.isNightHasPrecipitation())
                .build();
    }
}
