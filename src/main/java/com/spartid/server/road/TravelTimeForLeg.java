package com.spartid.server.road;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelTimeForLeg {
	private final String id;
	private final BasicDataValue basicDataValue;

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class BasicDataValue {
		private final LocalDateTime time;
		private final long travelTime;
		private final TravelTimeTrendType travelTimeTrendType;
		private final TravelTimeType travelTimeType;

		public BasicDataValue(
				@JsonProperty("time") LocalDateTime time,
				@JsonProperty("travelTime") long travelTime,
				@JsonProperty("travelTimeTrendType") TravelTimeTrendType travelTimeTrendType,
				@JsonProperty("travelTimeType") TravelTimeType travelTimeType) {
			super();
			this.time = time;
			this.travelTime = travelTime;
			this.travelTimeTrendType = travelTimeTrendType;
			this.travelTimeType = travelTimeType;
		}

	}

	@JsonCreator
	public TravelTimeForLeg(@JsonProperty("id") String id,
			@JsonProperty("basicDataValue") BasicDataValue basicDataValue) {
		this.id = id;
		this.basicDataValue = basicDataValue;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getTime() {
		return basicDataValue.time;
	}

	public long getTravelTime() {
		return basicDataValue.travelTime;
	}

	public TravelTimeTrendType getTravelTimeTrendType() {
		return basicDataValue.travelTimeTrendType;
	}

	public TravelTimeType getTravelTimeType() {
		return basicDataValue.travelTimeType;
	}

}
