package com.spartid.server.google;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleMapsService {

    private GoogleSettings googleSettings;
    private RestTemplate restTemplate;

    @Autowired
    public GoogleMapsService(GoogleSettings googleSettings, RestTemplate restTemplate) {
        this.googleSettings = googleSettings;
        this.restTemplate = restTemplate;
    }

    public GoogleDirections getRoute(GoogleRoute route) {
        URI url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/directions/json")
                .queryParam("origin", route.getFrom())
                .queryParam("destination", route.getTo())
                .queryParam("departure_time", "now")
                .queryParam("mode", "driving")
                .queryParam("key", googleSettings.getApiKey())
                .build().toUri();
        return restTemplate.getForObject(url, GoogleDirections.class);
    }

}
