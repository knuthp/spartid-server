package com.spartid.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.spartid.server.road.TravelTimeData;
import com.spartid.server.road.TravelTimeService;

@Component
@Profile("poller")
public class PollScheduleMain implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(PollScheduleMain.class);
	private ApplicationContext appContext;
	private TravelTimeService service;

	@Autowired
	public PollScheduleMain(TravelTimeService service, ApplicationContext appContext) {
		this.service = service;
		this.appContext = appContext;
	}

	@Override
	public void run(String[] args) {
		TravelTimeData travelTime = service.getTravelTime();
		LOG.warn("data={}", travelTime.getLegsTravelTime());
		terminate();
	}

	public void terminate() {
		SpringApplication.exit(appContext, () -> 0);
	}

}
