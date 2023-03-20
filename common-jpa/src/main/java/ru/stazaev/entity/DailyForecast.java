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
        return "DailyForecast{" +
                "id=" + id +
                ", code=" + code +
                ", date=" + date +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                ", dayWeather='" + dayWeather + '\'' +
                ", dayHasPrecipitation=" + dayHasPrecipitation +
                ", dayPrecipitationType='" + dayPrecipitationType + '\'' +
                ", dayPrecipitationIntensity='" + dayPrecipitationIntensity + '\'' +
                ", nightWeather='" + nightWeather + '\'' +
                ", nightHasPrecipitation=" + nightHasPrecipitation +
                ", nightPrecipitationType='" + nightPrecipitationType + '\'' +
                ", nightPrecipitationIntensity='" + nightPrecipitationIntensity + '\'' +
                '}';
    }
}
