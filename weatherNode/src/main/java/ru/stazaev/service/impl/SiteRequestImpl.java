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
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONArray(response.body()).getJSONObject(0);
            return jsonObject.getInt("Key");
        }catch (Exception e){
            log.error(e);
        }
        return -1;
    }
}
