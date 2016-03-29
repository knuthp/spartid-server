package com.spartid.server.road;

import com.spartid.server.road.TravelTimeData.PayloadPublication.PredefinedLocationContainer;

public class LegLocation {
    private final long id;
    private final String name;
    private final LinearCoordinates linearCoordinates;

    public LegLocation(long id, String name) {
        this.id = id;
        this.name = name;
        this.linearCoordinates = new LinearCoordinates();
    }

    public LegLocation(PredefinedLocationContainer e) {
        id = e.getId();
        name = e.getPredefinedLocationName();
        linearCoordinates = e.getLinearCoordinates();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinearCoordinates getCoordinates() {
        return linearCoordinates;
    }

}
