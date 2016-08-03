package com.spartid.server.road;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spartid.server.controller.TravelTime;
import com.spartid.server.route.Route;

@Component
public class TravelTimeLookup {

    private static final Logger LOG = LoggerFactory.getLogger(TravelTimeLookup.class);
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
        LOG.info("Getting locations for route with id={}", id);
        LegLocation legLocation = locationsLookup.getForId(id);
        LOG.info("Got locations for route with id={}", id);
        if (next.isBefore(LocalDateTime.now())) {
            LOG.info("Next time is before now next={}", next);
            travelTImeData = travelTimeService.getTravelTime();
            next = LocalDateTime.now().plusMinutes(1);
        } else {
            LOG.info("Next time is NOT before now next={}", next);
        }
        LegTravelTime legTravelTime = travelTImeData.getLegTravelTime(id);
        LOG.info("LegTravelTime={}", legTravelTime);
        LegTravelTimeEnriched legTravelTimeEnriched = new LegTravelTimeEnriched(legTravelTime, legLocation);
        LOG.info("LegTravelTimeEnriched={}", legTravelTimeEnriched);
        return legTravelTimeEnriched;
    }

    public TravelTime getTravelTimeDate(Route route) {
        return new TravelTime(route.getLegIds().stream().map(e -> getTravelTimeData(e)).collect(Collectors.toList()));
    }
}
