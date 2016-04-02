package com.spartid.server.route;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spartid.server.geoconversion.GeoLocation2D;
import com.spartid.server.geoconversion.UtmPosition;
import com.spartid.server.geoconversion.UtmToLatLong;
import com.spartid.server.google.GoogleRoute;
import com.spartid.server.road.LegLocation;

public class Route {

    private static final Logger LOG = LoggerFactory.getLogger(Route.class);
    public final int id;
    public final String name;
    public final List<LegLocation> legs;

    public Route(int id, String name, List<LegLocation> legs) {
        super();
        this.id = id;
        this.name = name;
        this.legs = legs;
    }

    public List<Long> getLegIds() {
        return legs.stream().map(LegLocation::getId).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<LegLocation> getLegs() {
        return legs;
    }

    private UtmPosition getStartPos() {
        LegLocation legLocation = getLegs().get(0);
        LOG.info("routeId={}, startLeg={}", id, legLocation.getName());
        return legLocation.getCoordinatesUtm().end();
    }

    private UtmPosition getEndPos() {
        return getLegs().get(getLegs().size() - 1).getCoordinatesUtm().start();
    }

    public GoogleRoute getGoogleRoute(UtmToLatLong converter) {
        GeoLocation2D start = converter.convertFromUtmToWgs84(getStartPos());
        GeoLocation2D end = converter.convertFromUtmToWgs84(getEndPos());
        return new GoogleRoute(start, end);
    }

}
