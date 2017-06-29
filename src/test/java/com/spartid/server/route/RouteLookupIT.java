package com.spartid.server.route;

import static org.hamcrest.CoreMatchers.not; 
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spartid.server.SparTidServerMain;
import com.spartid.server.road.LocationsLookup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SparTidServerMain.class)
@ActiveProfiles("secure")
public class RouteLookupIT {
    @Configuration
    @ComponentScan(basePackageClasses = LocationsLookup.class)
    @EnableConfigurationProperties
    static class ContextConfiguration {
    }

    @Autowired
    private RouteLookup routeLookup;
    @Autowired
    private LocationsLookup locationsLookup;

    @Test
    public void testCallExistingRouteReturnsRoute() {
        Route route = routeLookup.getRoute(1);

        assertThat(route, not(nullValue()));
    }
}
