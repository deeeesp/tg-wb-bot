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
@Table(name = "hourly_forecast")
public class HourlyForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int code;
    private Date date;
    private String weather;

    private int temperature;
    private boolean hasPrecipitation;
    private int precipitationProbability;
    private String precipitationType;
    private String precipitationIntensity;


    @Override
    public String toString() {
        return  "Дата " + date + "\n" +
                "Погода " + weather + '\'' + "\n" +
                "Будут ли осадки " + hasPrecipitation + "\n" +
                "Вероятность осадков " + precipitationProbability + "\n" +
                "Тип осадков " + precipitationType + '\'' + "\n" +
                "Сила осадков " + precipitationIntensity + '\'' + "\n";
    }
}
