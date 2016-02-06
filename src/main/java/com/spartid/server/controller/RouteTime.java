package com.spartid.server.controller;

import java.util.List;

import com.spartid.server.road.LegTravelTime;

public class RouteTime {
	private final long id;
	private final String name;
	private final List<LegTravelTime> legs;

	public RouteTime(long id, String name, List<LegTravelTime> legs) {
		this.id = id;
		this.name = name;
		this.legs = legs;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getTravelTime() {
		return legs.stream().mapToDouble(e -> e.getTravelTime()).sum();
	}

	public double getFreeFlowTime() {
		return legs.stream().mapToDouble(e -> e.getFreeFlowTime()).sum();
	}

	public List<LegTravelTime> getLegs() {
		return legs;
	}

}
