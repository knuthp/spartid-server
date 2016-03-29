package com.spartid.server.road;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.spartid.server.geoconversion.UtmPosition;

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
                return Long.parseLong(basicData.pertinentLocation.predefinedLocationReference.id);
            }

            public TravelTimeTrendType getTravelTimeTrendType() {
                if (basicData.travelTimeTrendType == null) {
                    return TravelTimeTrendType.INVALID;
                }
                return TravelTimeTrendType.valueOf(basicData.travelTimeTrendType.toUpperCase());
            }

            public TravelTimeType getTravelTimeType() {
                if (basicData.travelTimeType == null) {
                    return TravelTimeType.INVALID;
                }
                return TravelTimeType.valueOf(basicData.travelTimeType.toUpperCase());
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

        static class PredefinedLocationContainer {

            static class PredefinedLocationName {
                static class Values {
                    @XmlElement(name = "value")
                    private List<String> values;
                }

                @XmlElement
                private Values values;

            }

            static class Location {
                static class LinearExtension {
                    static class LinearLineStringExtension {
                        static class GmlLineString {
                            @XmlElement
                            private String coordinates;
                        }

                        @XmlElement
                        private GmlLineString gmlLineString;
                    }

                    @XmlElement
                    private LinearLineStringExtension linearLineStringExtension;
                }

                @XmlElement
                private LinearExtension linearExtension;
            }

            @XmlAttribute
            private String id;
            @XmlElement
            private PredefinedLocationName predefinedLocationName;
            @XmlElement
            private Location location;

            public long getId() {
                return Long.parseLong(id);
            }

            public String getPredefinedLocationName() {
                return predefinedLocationName.values.values.get(0);
            }

            public LinearCoordinates getLinearCoordinates() {
                StringTokenizer tokenizerCoordinate = new StringTokenizer(location.linearExtension.linearLineStringExtension.gmlLineString.coordinates, ",");
                LinearCoordinates linearCoordinates = new LinearCoordinates();
                while (tokenizerCoordinate.hasMoreTokens()) {
                    StringTokenizer xyTokenizer = new StringTokenizer(tokenizerCoordinate.nextToken());
                    UtmPosition pos = new UtmPosition(Double.parseDouble(xyTokenizer.nextToken()), Double.parseDouble(xyTokenizer.nextToken()));
                    linearCoordinates.add(pos);
                }
                return linearCoordinates;
            }

        }

        @XmlElement
        private String publicationTime;

        @XmlElement
        private List<ElaboratedData> elaboratedData;

        @XmlElement
        private List<PredefinedLocationContainer> predefinedLocationContainer;
    }

    @XmlElement
    private PayloadPublication payloadPublication;

    public String getPublicationTime() {
        return payloadPublication.publicationTime;
    }

    public List<LegTravelTime> getLegsTravelTime() {
        return payloadPublication.elaboratedData.stream().map(e -> new LegTravelTime(e)).collect(Collectors.toList());
    }

    public List<LegLocation> getLegLocations() {
        return payloadPublication.predefinedLocationContainer.stream().map(e -> new LegLocation(e))
                .collect(Collectors.toList());
    }

    public LegTravelTime getLegTravelTime(long id) {
        return getLegsTravelTime().stream().filter(l -> l.getId() == id).findFirst().get();
    }

    public LegLocation getLegLocation(long id) {
        return getLegLocations().stream().filter(l -> l.getId() == id).findFirst().get();
    }

}
