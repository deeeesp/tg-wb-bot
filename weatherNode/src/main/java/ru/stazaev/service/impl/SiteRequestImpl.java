package ru.stazaev.service.impl;

import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.stazaev.service.SiteRequest;
import ru.stazaev.service.UriGenerator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Log4j2
public class SiteRequestImpl implements SiteRequest {
    private final UriGenerator uriGenerator;

    public SiteRequestImpl(UriGenerator uriGenerator) {
        this.uriGenerator = uriGenerator;
    }

    @Override
    public int getCityCode(String city) {
        var uri = uriGenerator.generateCityCodeUri(city);
        var request = sendRequest(uri);
        if (request!= null && request.statusCode() == 200) {
            JSONObject jsonObject = new JSONArray(request).getJSONObject(0);
            return jsonObject.getInt("Key");
        }
        return -1;
    }

    @Override
    public HttpResponse<String> getHourlyForecast(int city) {
        var uri = uriGenerator.generateHourlyForecastUri(city);
        var request = sendRequest(uri);
        System.out.println(request);
        if (request!= null && request.statusCode() == 200) {
            return request;
        }
        return null;
    }

    @Override
    public HttpResponse<String> getDailyForecast(int city) {
        var uri = uriGenerator.generateDailyForecastUri(city);
        var request = sendRequest(uri);
        if (request!= null && request.statusCode() == 200) {
            return request;
        }
        return null;
    }

    private HttpResponse<String> sendRequest(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}
