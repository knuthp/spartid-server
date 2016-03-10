package com.spartid.server.google;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleDirections {
    public static class KeyValue {
        private final String text;
        private final String value;

        @JsonCreator
        public KeyValue(@JsonProperty("text") String text, @JsonProperty("value") String value) {
            this.text = text;
            this.value = value;
        }
    }

    public static class Leg {
        private final KeyValue duration;
        private final KeyValue durationInTraffic;

        @JsonCreator
        public Leg(@JsonProperty("duration") KeyValue duration, @JsonProperty("duration_in_traffic") KeyValue durationInTraffic) {
            this.duration = duration;
            this.durationInTraffic = durationInTraffic;
        }

        public int getDurationInTraffic() {
            return Integer.parseInt(durationInTraffic.value);
        }
    }

    public static class Route {
        private final List<Leg> legs;

        @JsonCreator
        public Route(@JsonProperty("legs") List<Leg> legs) {
            this.legs = legs;
        }
    }

    private final List<Route> routes;

    @JsonCreator
    public GoogleDirections(@JsonProperty("routes") List<Route> routes) {
        this.routes = routes;
    }

    public int getDurationInTraffic() {
        return routes.stream().findAny().map(e -> sumDurationInTraffic(e.legs)).orElseThrow(IllegalArgumentException::new);
    }

    private int sumDurationInTraffic(List<Leg> legs) {
        return legs.stream().mapToInt(e -> e.getDurationInTraffic()).sum();
    }
}
