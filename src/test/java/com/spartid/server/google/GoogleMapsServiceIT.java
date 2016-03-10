package com.spartid.server.google;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration
public class GoogleMapsServiceIT {
    private static final GoogleRoute LYSAKER_ASKER = new GoogleRoute("59.9134717,10.6415964", "59.8359977,10.4456635");
    private static final GoogleRoute ASKER_LYSAKER = new GoogleRoute("59.8339662,10.441999", "59.9126466,10.6370975");

    @Configuration
    @ComponentScan
    @EnableConfigurationProperties
    static class ContextConfiguration {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @Autowired
    private GoogleMapsService service;

    @Test
    public void testCallsLysakerAsker() {
        GoogleDirections routeTime = service.getRoute(LYSAKER_ASKER);

        assertThat(routeTime.getDurationInTraffic(), greaterThanOrEqualTo(500));
    }

    @Test
    public void testCallsAskerLysaker() {
        GoogleDirections routeTime = service.getRoute(ASKER_LYSAKER);

        assertThat(routeTime.getDurationInTraffic(), greaterThanOrEqualTo(500));
    }

}
