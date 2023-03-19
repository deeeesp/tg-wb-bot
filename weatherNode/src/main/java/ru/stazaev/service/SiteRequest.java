package ru.stazaev.service;

import org.json.JSONObject;

import java.net.http.HttpResponse;

public interface SiteRequest {
    int getCityCode(String city);
    HttpResponse<String> getHourlyForecast(int city);
    HttpResponse<String> getDailyForecast(int city);

}
