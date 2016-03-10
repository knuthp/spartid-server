package com.spartid.server.google;

public class GoogleRoute {
    private final String from;
    private final String to;

    public GoogleRoute(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
