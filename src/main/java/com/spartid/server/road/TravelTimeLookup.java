package com.spartid.server.road;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TravelTimeLookup {

    private LocationsLookup locationsLookup;
    private TravelTimeService travelTimeService;

    @Autowired
    public TravelTimeLookup(LocationsLookup locationsLookup, TravelTimeService travelTimeService) {
        this.locationsLookup = locationsLookup;
        this.travelTimeService = travelTimeService;
    }

    public LegTravelTimeEnriched getTravelTimeData(long id) {
        LegLocation legLocation = locationsLookup.getForId(id);
        LegTravelTime legTravelTime = travelTimeService.getTravelTime().getLegTravelTime(id);
        LegTravelTimeEnriched legTravelTimeEnriched = new LegTravelTimeEnriched(legTravelTime, legLocation);
        return legTravelTimeEnriched;
    }
}
