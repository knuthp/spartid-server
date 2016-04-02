package com.spartid.server.route;

import java.util.List;
import java.util.stream.Collectors;

import com.spartid.server.road.LegLocation;

public class Route {
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

}
