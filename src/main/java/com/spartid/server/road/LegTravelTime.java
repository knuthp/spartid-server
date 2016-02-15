package com.spartid.server.road;

import com.google.common.base.MoreObjects;
import com.spartid.server.road.TravelTimeData.PayloadPublication.ElaboratedData;

public class LegTravelTime {
	private long id;
	private TravelTimeTrendType travelTimeTrendType;
	private TravelTimeType travelTimeType;
	private double travelTime;
	private double freeFlowTime;

	LegTravelTime(ElaboratedData e) {
		this.id = e.getId();
		this.travelTimeTrendType = e.getTravelTimeTrendType();
		this.travelTimeType = e.getTravelTimeType();
		this.travelTime = e.getTravelTime();
		this.freeFlowTime = e.getFreeFlowTime();
	}

	public long getId() {
		return id;
	}

	public TravelTimeTrendType getTravelTimeTrendType() {
		return travelTimeTrendType;
	}

	public TravelTimeType getTravelTimeType() {
		return travelTimeType;
	}

	public double getTravelTime() {
		return travelTime;
	}

	public double getFreeFlowTime() {
		return freeFlowTime;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("freeFlowTime", freeFlowTime)
				.add("travelTime", travelTime).add("travelTimeTrendType", travelTimeTrendType)
				.add("travelTimeType", travelTimeType).toString();
	}
}
