package ru.stazaev.entity.dto.forecast;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class DailyWithDayPrecipitationDTO extends Forecast{
    private Date date;
    private int maxTemperature;
    private int minTemperature;
    private String dayWeather;
    private boolean dayHasPrecipitation;
    private String dayPrecipitationType;
    private String dayPrecipitationIntensity;
    private String nightWeather;
    private boolean nightHasPrecipitation;

    @Override
    public String toString() {
        return  "Дата " + date + "\n" +
                "Максимальная температура " + maxTemperature + "\n" +
                "Минимальная температура " + minTemperature + "\n" +
                "\n" +
                "День \n" +
                "Погода " + dayWeather + "\n" +
                "Будут ли осадки " + dayHasPrecipitation + "\n" +
                "Тип осадков " + dayPrecipitationType + "\n" +
                "Сила садков " + dayPrecipitationIntensity + "\n" +
                "\n" +
                "Ночь \n" +
                "Погода " + nightWeather + "\n" +
                "Будут ли осадки " + nightHasPrecipitation + "\n";
    }
}
