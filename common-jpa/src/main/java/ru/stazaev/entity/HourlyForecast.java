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
        return "HourlyForecast{" +
                "id=" + id +
                ", code=" + code +
                ", date=" + date +
                ", weather='" + weather + '\'' +
                ", hasPrecipitation=" + hasPrecipitation +
                ", precipitationProbability=" + precipitationProbability +
                ", precipitationType='" + precipitationType + '\'' +
                ", precipitationIntensity='" + precipitationIntensity + '\'' +
                '}';
    }
}
