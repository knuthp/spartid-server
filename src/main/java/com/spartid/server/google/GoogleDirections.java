package com.spartid.server.google;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleDirections {
    public static class TextValue {
        private final String text;
        private final String value;

        @JsonCreator
        public TextValue(@JsonProperty("text") String text, @JsonProperty("value") String value) {
            this.text = text;
            this.value = value;
        }
    }

    public static class Leg {
        private final TextValue duration;
        private final TextValue durationInTraffic;

        @JsonCreator
        public Leg(@JsonProperty("duration") TextValue duration, @JsonProperty("duration_in_traffic") TextValue durationInTraffic) {
            this.duration = duration;
            this.durationInTraffic = durationInTraffic;
        }

        public int getDurationInTraffic() {
            return Integer.parseInt(durationInTraffic.value);
        }

        public int getDuration() {
            return Integer.parseInt(duration.value);
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

    public int getDuration() {
        return routes.stream().findAny().map(e -> sumDuration(e.legs)).orElseThrow(IllegalArgumentException::new);
    }

    private int sumDurationInTraffic(List<Leg> legs) {
        return legs.stream().mapToInt(e -> e.getDurationInTraffic()).sum();
    }

    private int sumDuration(List<Leg> legs) {
        return legs.stream().mapToInt(e -> e.getDuration()).sum();
    }
}
