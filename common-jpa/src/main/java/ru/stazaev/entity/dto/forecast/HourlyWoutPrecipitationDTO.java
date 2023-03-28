package ru.stazaev.entity.dto.forecast;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class HourlyWoutPrecipitationDTO extends Forecast{
    private Date date;
    private String weather;
    private int temperature;
    private boolean hasPrecipitation;
    private int precipitationProbability;

    @Override
    public String toString() {
        return "Дата " + date + "\n" +
                "Погода " + weather + "\n" +
                "Будут ли осадки " + hasPrecipitation + "\n" +
                "Вероятность осадков " + precipitationProbability + "\n";
    }
}
