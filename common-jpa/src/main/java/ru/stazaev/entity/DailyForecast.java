package ru.stazaev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_forecast")
public class DailyForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int code;
    private Date date;
    private int maxTemperature;
    private int minTemperature;
    private String dayWeather;
    private boolean dayHasPrecipitation;
    private String dayPrecipitationType;
    private String dayPrecipitationIntensity;
    private String nightWeather;
    private boolean nightHasPrecipitation;
    private String nightPrecipitationType;
    private String nightPrecipitationIntensity;

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
                "Будут ли осадки " + nightHasPrecipitation + "\n" +
                "Тип осадков " + nightPrecipitationType + "\n" +
                "Сила садков " + nightPrecipitationIntensity + "\n";
    }
}
