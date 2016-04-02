package com.spartid.server.road;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationsLookup {
    private final Map<Long, LegLocation> idToName = new HashMap<>();
    private final TravelTimeService travelTimeService;

    @Autowired
    public LocationsLookup(TravelTimeService travelTimeService) {
        this.travelTimeService = travelTimeService;
        TravelTimeData travelLocations = travelTimeService.getTravelLocations();
        for (LegLocation leg : travelLocations.getLegLocations()) {
            idToName.put(leg.getId(), leg);
        }
    }

    public LegLocation getForId(long id) {
        return idToName.get(id);
    }
}
