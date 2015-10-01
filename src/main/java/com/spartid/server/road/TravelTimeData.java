package com.spartid.server.road;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "d2LogicalModel", namespace = "http://datex2.eu/schema/2/2_0")
public class TravelTimeData {

	static class PayloadPublication {

		static class ElaboratedData {
			static class BasicData {
				static class PertinentLocation {
					static class PredefinedLocationReference {
						@XmlAttribute
						private String id;
					}

					@XmlElement
					private PredefinedLocationReference predefinedLocationReference;
				}

				static class Duration {
					@XmlElement
					private double duration;
				}

				@XmlElement
				private PertinentLocation pertinentLocation;
				@XmlElement
				private String travelTimeTrendType;
				@XmlElement
				public String travelTimeType;
				@XmlElement
				public Duration travelTime;
				@XmlElement
				public Duration freeFlowTravelTime;
			}

			@XmlElement
			private BasicData basicData;

			public long getId() {
				return Long
						.parseLong(basicData.pertinentLocation.predefinedLocationReference.id);
			}

			public TravelTimeTrendType getTravelTimeTrendType() {
				if (basicData.travelTimeTrendType == null) {
					return TravelTimeTrendType.INVALID;
				}
				return TravelTimeTrendType
						.valueOf(basicData.travelTimeTrendType.toUpperCase());
			}

			public TravelTimeType getTravelTimeType() {
				if (basicData.travelTimeType == null) {
					return TravelTimeType.INVALID;
				}
				return TravelTimeType.valueOf(basicData.travelTimeType
						.toUpperCase());
			}

			public double getTravelTime() {
				if (basicData.travelTime == null) {
					return 0.0;
				}
				return basicData.travelTime.duration;
			}

			public double getFreeFlowTime() {
				if (basicData.freeFlowTravelTime == null) {
					return 0.0;
				}
				return basicData.freeFlowTravelTime.duration;
			}
		}

		@XmlElement
		private String publicationTime;

		@XmlElement
		private List<ElaboratedData> elaboratedData;
	}

	@XmlElement
	private PayloadPublication payloadPublication;

	public String getPublicationTime() {
		return payloadPublication.publicationTime;
	}

	public List<LegTravelTime> getLegsTravelTime() {
		return payloadPublication.elaboratedData.stream()
				.map(e -> new LegTravelTime(e)).collect(Collectors.toList());
	}
}
