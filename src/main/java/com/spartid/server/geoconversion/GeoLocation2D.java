package com.spartid.server.geoconversion;

public class GeoLocation2D {
    private final double latitude;
    private final double longitude;

    public GeoLocation2D(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLat() {
        return latitude;
    }

    public double getLong() {
        return longitude;
    }

}
