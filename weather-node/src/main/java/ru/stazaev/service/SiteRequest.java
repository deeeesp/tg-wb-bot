package ru.stazaev.service;

import java.net.http.HttpResponse;

public interface SiteRequest {
    HttpResponse<String> getCityCode(String city);
    HttpResponse<String> getHoursForecast(int city, int count);
    HttpResponse<String> getDaysForecast(int city, int count);

}
