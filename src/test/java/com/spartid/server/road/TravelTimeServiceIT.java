package com.spartid.server.road;

import static org.hamcrest.Matchers.equalTo; 
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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

import com.spartid.server.geoconversion.UtmPosition;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("secure")
public class TravelTimeServiceIT {

    private static final long ID_ASKER_HOLMEN = 100098L;

    @Configuration
    @ComponentScan
    @EnableConfigurationProperties
    static class ContextConfiguration {
    }

    @Autowired
    private TravelTimeService travelTimeService;

    @Test
    public void testContainsData() {
        TravelTimeData travelTimeData = travelTimeService.getTravelTime();
        assertThat(travelTimeData.getLegsTravelTime().size(), greaterThan(10));
    }

    @Test
    public void testContainsKnownData() {
        TravelTimeData travelTimeData = travelTimeService.getTravelTime();
        LegTravelTime leg = travelTimeData.getLegTravelTime(ID_ASKER_HOLMEN);
        assertThat(leg.getFreeFlowTime(), equalTo(160.0));
        assertThat(leg.getTravelTime(), greaterThanOrEqualTo(160.0));
        // assertThat(leg.getTravelTimeTrendType(),
        // equalTo(TravelTimeTrendType.STABLE));
        assertThat(leg.getTravelTimeTrendType(), equalTo(TravelTimeTrendType.STABLE));
    }

    @Test
    public void testLocationsBasic() throws Exception {
        TravelTimeData travelTimeDate = travelTimeService.getTravelLocations();
        LegLocation legLocation = travelTimeDate.getLegLocation(ID_ASKER_HOLMEN);
        assertThat(legLocation.getId(), equalTo(ID_ASKER_HOLMEN));
        assertThat(legLocation.getName(), equalTo("Asker - Holmen"));
    }

    @Test
    public void testLegLocationPositions() throws Exception {
        TravelTimeData travelTimeData = travelTimeService.getTravelLocations();
        LegLocation legLocation = travelTimeData.getLegLocation(ID_ASKER_HOLMEN);

        LinearCoordinates positions = legLocation.getCoordinatesUtm();

        assertThat(positions.size(), equalTo(58));
        assertThat(positions.start(), equalTo(new UtmPosition(247194, 6644353)));
        assertThat(positions.end(), equalTo(new UtmPosition(244476, 6641604)));
    }
}
