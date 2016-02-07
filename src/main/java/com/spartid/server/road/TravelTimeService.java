package com.spartid.server.road;

import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TravelTimeService {
	private RestTemplate restTemplate;
	private URI realtimeUrl;
	private URI locationsUrl;
	private VegvesenSettings settings;

	@Autowired
	public TravelTimeService(VegvesenSettings settings) {
		this.settings = settings;
		restTemplate = new RestTemplate();
		realtimeUrl = URI.create(settings.getTravelTimeDataUrl());
		locationsUrl = URI.create(settings.getPredefinedTravelTimeLocationsUrl());
	}

	public TravelTimeData getTravelTime() {
		ResponseEntity<TravelTimeData> response = restTemplate.exchange(realtimeUrl, HttpMethod.GET,
				new HttpEntity(createHeaders(settings.getUsername(), settings.getPassword())), TravelTimeData.class);
		return response.getBody();

	}

	public TravelTimeData getTravelLocations() {
		ResponseEntity<TravelTimeData> response = restTemplate.exchange(locationsUrl, HttpMethod.GET,
				new HttpEntity(createHeaders(settings.getUsername(), settings.getPassword())), TravelTimeData.class);
		return response.getBody();

	}

	HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

}
