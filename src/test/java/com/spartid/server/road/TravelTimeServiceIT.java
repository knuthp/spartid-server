package com.spartid.server.road;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class TravelTimeServiceIT {

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
		LegTravelTime leg = travelTimeData.getLeg(100098L);
		assertThat(leg.getFreeFlowTime(), equalTo(160.0));
		assertThat(leg.getTravelTime(), greaterThanOrEqualTo(160.0));
		// assertThat(leg.getTravelTimeTrendType(),
		// equalTo(TravelTimeTrendType.STABLE));
		assertThat(leg.getTravelTimeTrendType(), equalTo(TravelTimeTrendType.STABLE));
	}

}
