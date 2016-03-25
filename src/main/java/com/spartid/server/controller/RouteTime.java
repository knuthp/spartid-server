package com.spartid.server.controller;

import java.util.Map;

public class RouteTime {
    private final long id;
    private final String name;
    private final Map<Provider, TravelTime> map;

    public RouteTime(long id, String name, Map<Provider, TravelTime> map) {
        this.id = id;
        this.name = name;
        this.map = map;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Provider, TravelTime> getProviderData() {
        return map;
    }
}
