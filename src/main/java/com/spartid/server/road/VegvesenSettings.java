package com.spartid.server.road;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vegvesen")
public class VegvesenSettings {

    private String travelTimeDataUrl;
    private String predefinedTravelTimeLocationsUrl;
    @NotNull
    private String username;
    @NotNull
    private String password;

    public String getTravelTimeDataUrl() {
        return travelTimeDataUrl;
    }

    public void setTravelTimeDataUrl(String travelTimeDataUrl) {
        this.travelTimeDataUrl = travelTimeDataUrl;
    }

    public String getPredefinedTravelTimeLocationsUrl() {
        return predefinedTravelTimeLocationsUrl;
    }

    public void setPredefinedTravelTimeLocationsUrl(String predefinedTravelTimeLocationsUrl) {
        this.predefinedTravelTimeLocationsUrl = predefinedTravelTimeLocationsUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}