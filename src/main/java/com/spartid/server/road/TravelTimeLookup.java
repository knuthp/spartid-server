package com.spartid.server.road;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spartid.server.controller.TravelTime;
import com.spartid.server.route.Route;

@Component
public class TravelTimeLookup {

    private LocationsLookup locationsLookup;
    private TravelTimeService travelTimeService;
    private TravelTimeData travelTImeData;
    private LocalDateTime next = LocalDateTime.MIN;

    @Autowired
    public TravelTimeLookup(LocationsLookup locationsLookup, TravelTimeService travelTimeService) {
        this.locationsLookup = locationsLookup;
        this.travelTimeService = travelTimeService;
        this.travelTImeData = travelTimeService.getTravelTime();
    }

    public LegTravelTimeEnriched getTravelTimeData(long id) {
        LegLocation legLocation = locationsLookup.getForId(id);
        if (next.isBefore(LocalDateTime.now())) {
            travelTImeData = travelTimeService.getTravelTime();
            next = LocalDateTime.now().plusMinutes(1);
        }
        LegTravelTime legTravelTime = travelTImeData.getLegTravelTime(id);
        LegTravelTimeEnriched legTravelTimeEnriched = new LegTravelTimeEnriched(legTravelTime, legLocation);
        return legTravelTimeEnriched;
    }

    public TravelTime getTravelTimeDate(Route route) {
        return new TravelTime(route.getLegIds().stream().map(e -> getTravelTimeData(e)).collect(Collectors.toList()));
    }
}
