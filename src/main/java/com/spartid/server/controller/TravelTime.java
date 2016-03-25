package com.spartid.server.controller;

import java.util.List;

import com.spartid.server.road.LegTravelTimeEnriched;

public class TravelTime {
    private final List<LegTravelTimeEnriched> legs;

    public TravelTime(List<LegTravelTimeEnriched> legs) {
        this.legs = legs;
    }

    public double getTravelTime() {
        return legs.stream().mapToDouble(e -> e.getTravelTime()).sum();
    }

    public double getFreeFlowTime() {
        return legs.stream().mapToDouble(e -> e.getFreeFlowTime()).sum();
    }

    public List<LegTravelTimeEnriched> getLegs() {
        return legs;
    }

}
