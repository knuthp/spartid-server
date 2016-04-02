package com.spartid.server.route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spartid.server.road.LegLocation;
import com.spartid.server.road.LocationsLookup;

@Component
public class RouteLookup {
    private final Map<Integer, Route> routes = new HashMap<Integer, Route>();
    private final LocationsLookup locationsLookup;

    @Autowired
    public RouteLookup(LocationsLookup locationsLookup) {
        this.locationsLookup = locationsLookup;
        addRoute(1, "Asker - Lysaker", 100098, 100159, 100160);
        addRoute(2, "Lysaker - Asker", 100161, 100162, 100101);
        addRoute(3, "Sandvika - Sollihøgda", 100256, 100265);
        addRoute(4, "Sollihøgda - Sandvika", 100264, 100257);
    }

    private void addRoute(int routeId, String routeName, Integer... legIdArray) {
        List<Integer> legIds = Arrays.asList(legIdArray);
        List<LegLocation> legs = legIds.stream().map(e -> this.locationsLookup.getForId(e)).collect(Collectors.toList());
        Route route = new Route(routeId, routeName, legs);
        routes.put(routeId, route);
    }

    public List<Route> getRoutes() {
        List<Route> aList = new ArrayList<Route>();
        SortedSet<Integer> keys = new TreeSet<Integer>(routes.keySet());
        for (Integer key : keys) {
            aList.add(routes.get(key));
        }
        return aList;
    }

    public Route getRoute(int id) {
        return routes.get(id);
    }
}
