package com.spartid.server.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spartid.server.geoconversion.GeoLocation2D;
import com.spartid.server.google.GoogleDirections;
import com.spartid.server.google.GoogleMapsService;
import com.spartid.server.google.GoogleRoute;
import com.spartid.server.road.LegLocation;
import com.spartid.server.road.LegTravelTime;
import com.spartid.server.road.LegTravelTimeEnriched;
import com.spartid.server.road.TravelTimeData;
import com.spartid.server.road.TravelTimeLookup;
import com.spartid.server.road.TravelTimeService;
import com.spartid.server.route.Route;
import com.spartid.server.route.RouteLookup;

@RestController
public class MainController {
    public static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    private final TravelTimeService travelTimeService;
    private final TravelTimeLookup travelTimeLookup;
    private final GoogleMapsService googleMapsService;
    private static final GoogleRoute LYSAKER_ASKER = new GoogleRoute(new GeoLocation2D(59.9134717, 10.6415964), new GeoLocation2D(59.8359977, 10.4456635));
    private static final GoogleRoute ASKER_LYSAKER = new GoogleRoute(new GeoLocation2D(59.8339662, 10.441999), new GeoLocation2D(59.9126466, 10.6370975));
    private RouteLookup routeLookup;

    @Autowired
    public MainController(TravelTimeService travelTimeService, TravelTimeLookup travelTimeLookup, GoogleMapsService googleMapsService, RouteLookup routeLookup) {
        this.travelTimeService = travelTimeService;
        this.travelTimeLookup = travelTimeLookup;
        this.googleMapsService = googleMapsService;
        this.routeLookup = routeLookup;
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
        Map<Provider, TravelTime> map = new HashMap<>();
        Route route = routeLookup.getRoute(2);
        map.put(Provider.VEGVESEN, travelTimeLookup.getTravelTimeDate(route));
        GoogleDirections googleDirections = googleMapsService.getRoute(LYSAKER_ASKER);
        map.put(Provider.GOOGLE,
                new TravelTime(Arrays.asList(new LegTravelTimeEnriched(new LegTravelTime(googleDirections), new LegLocation(id, "Lysaker - Asker")))));
        return new RouteTime(route, map);
    }

    private RouteTime getRouteTimeAskerLysaker(long id) {
        Map<Provider, TravelTime> map = new HashMap<>();
        Route route = routeLookup.getRoute(1);
        map.put(Provider.VEGVESEN, travelTimeLookup.getTravelTimeDate(route));
        GoogleDirections googleDirections = googleMapsService.getRoute(ASKER_LYSAKER);
        map.put(Provider.GOOGLE,
                new TravelTime(Arrays.asList(new LegTravelTimeEnriched(new LegTravelTime(googleDirections), new LegLocation(id, "Asker - Lysaker")))));
        return new RouteTime(route, map);
    }

    private RouteTime getRouteTimeSandvikaSollihogda(long id) {
        Map<Provider, TravelTime> map = new HashMap<>();
        Route route = routeLookup.getRoute(3);
        map.put(Provider.VEGVESEN, travelTimeLookup.getTravelTimeDate(route));
        return new RouteTime(route, map);
    }

    private RouteTime getRouteTimeSollihogdaSandvika(long id) {
        Map<Provider, TravelTime> map = new HashMap<>();
        Route route = routeLookup.getRoute(4);
        map.put(Provider.VEGVESEN, travelTimeLookup.getTravelTimeDate(route));
        return new RouteTime(route, map);
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
