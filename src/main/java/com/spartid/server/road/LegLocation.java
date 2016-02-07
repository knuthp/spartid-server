package com.spartid.server.road;

import com.spartid.server.road.TravelTimeData.PayloadPublication.PredefinedLocationContainer;

public class LegLocation {
	private long id;
	private String name;

	public LegLocation(PredefinedLocationContainer e) {
		id = e.getId();
		name = e.getPredefinedLocationName();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
