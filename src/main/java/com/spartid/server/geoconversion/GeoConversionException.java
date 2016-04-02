package com.spartid.server.geoconversion;

public class GeoConversionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GeoConversionException(Exception e) {
        super(e);
    }
}
