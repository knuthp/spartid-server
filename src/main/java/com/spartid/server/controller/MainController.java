package com.spartid.server.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spartid.server.road.LegLocation;
import com.spartid.server.road.LegTravelTime;
import com.spartid.server.road.LegTravelTimeEnriched;
import com.spartid.server.road.TravelTimeData;
import com.spartid.server.road.TravelTimeLookup;
import com.spartid.server.road.TravelTimeService;

@RestController
public class MainController {
    public static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    private final TravelTimeService travelTimeService;
    private final TravelTimeLookup travelTimeLookup;

    @Autowired
    public MainController(TravelTimeService travelTimeService, TravelTimeLookup travelTimeLookup) {
        this.travelTimeService = travelTimeService;
        this.travelTimeLookup = travelTimeLookup;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ApiRoot home() {
        LOG.info("Handling a request");
        ApiRoot apiRoot = new ApiRoot();
        apiRoot.add(linkTo(methodOn(this.getClass()).locations()).withRel("locations"));
        apiRoot.add(linkTo(methodOn(this.getClass()).traveltimes()).withRel("traveltimes"));
        apiRoot.add(linkTo(methodOn(this.getClass()).routetimes()).withRel("routetimes"));
        return apiRoot;
    }

    @RequestMapping(value = "/traveltimes", method = RequestMethod.GET)
    @ResponseBody
    public List<LegTravelTime> traveltimes() {
        LOG.info("Handling a request");
        return travelTimeService.getTravelTime().getLegsTravelTime();
    }

    @RequestMapping(value = "/traveltimes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LegTravelTimeEnriched legtime(@PathVariable("id") long id) {
        LOG.info("Handling a request");
        return travelTimeLookup.getTravelTimeData(id);
    }

    @RequestMapping(value = "routetimes", method = RequestMethod.GET)
    @ResponseBody
    public List<RouteTime> routetimes() {
        TravelTimeData travelTimeData = travelTimeService.getTravelTime();
        return Arrays.asList(getRouteTimeAskerLysaker(1),
                getRouteTimeLysakerAsker(2),
                getRouteTimeSandvikaSollihogda(3),
                getRouteTimeSollihogdaSandvika(4));
    }

    @RequestMapping(value = "routetimes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RouteTime routetime(@PathVariable("id") long id) {
        TravelTimeData travelTimeData = travelTimeService.getTravelTime();
        if (id == 1) {
            return getRouteTimeAskerLysaker(id);
        } else if (id == 2) {
            return getRouteTimeLysakerAsker(id);
        } else if (id == 3) {
            return getRouteTimeSandvikaSollihogda(id);
        } else if (id == 4) {
            return getRouteTimeSollihogdaSandvika(id);
        } else {
            return null;
        }

    }

    private RouteTime getRouteTimeLysakerAsker(long id) {
        return new RouteTime(id, "Lysaker - Asker", Arrays.asList(travelTimeLookup.getTravelTimeData(100161),
                travelTimeLookup.getTravelTimeData(100162), travelTimeLookup.getTravelTimeData(100101)));
    }

    private RouteTime getRouteTimeAskerLysaker(long id) {
        return new RouteTime(id, "Asker - Lysaker", Arrays.asList(travelTimeLookup.getTravelTimeData(100098),
                travelTimeLookup.getTravelTimeData(100159), travelTimeLookup.getTravelTimeData(100160)));
    }

    private RouteTime getRouteTimeSandvikaSollihogda(long id) {
        return new RouteTime(id, "Sandvika - Sollihøgda", Arrays.asList(travelTimeLookup.getTravelTimeData(100256), travelTimeLookup.getTravelTimeData(100265)));
    }

    private RouteTime getRouteTimeSollihogdaSandvika(long id) {
        return new RouteTime(id, "Sollihøgda - Sandvika", Arrays.asList(travelTimeLookup.getTravelTimeData(100264), travelTimeLookup.getTravelTimeData(100257)));
    }

    @RequestMapping(value = "locations", method = RequestMethod.GET)
    @ResponseBody
    public List<LegLocation> locations() {
        return travelTimeService.getTravelLocations().getLegLocations();
    }

    @RequestMapping(value = "locations/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LegLocation location(@PathVariable("id") long id) {
        return travelTimeService.getTravelLocations().getLegLocation(id);
    }

    @RequestMapping(value = "locations/{id}/rt", method = RequestMethod.GET)
    @ResponseBody
    public LegTravelTime legTravelTimeLocation(@PathVariable("id") long id) {
        return travelTimeService.getTravelTime().getLegTravelTime(id);
    }

}
