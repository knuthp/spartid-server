package com.spartid.server.road;


public class LegTravelTimeEnriched extends LegTravelTime {
    private final String name;

    public LegTravelTimeEnriched(LegTravelTime legTravelTime, LegLocation legLocation) {
        super(legTravelTime);
        this.name = legLocation.getName();
    }

    public String getName() {
        return name;
    }

}
