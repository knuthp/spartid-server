package com.spartid.server.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spartid.server.road.LegTravelTime;
import com.spartid.server.road.TravelTimeData;
import com.spartid.server.road.TravelTimeService;

@RestController
public class MainController {
	public static final Logger LOG = LoggerFactory.getLogger(MainController.class);
	private TravelTimeService travelTimeService;

	@Autowired
	public MainController(TravelTimeService travelTimeService) {
		this.travelTimeService = travelTimeService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> home() {
		LOG.info("Handling a request");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", "ok");

		return response;
	}

	@RequestMapping(value = "/traveltimes", method = RequestMethod.GET)
	@ResponseBody
	public TravelTimeData traveltimes() {
		LOG.info("Handling a request");
		return travelTimeService.getTravelTime();
	}

	@RequestMapping(value = "/traveltimes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LegTravelTime legtime(@PathVariable("id") long id) {
		LOG.info("Handling a request");
		return travelTimeService.getTravelTime().getLeg(id);
	}

	@RequestMapping(value = "routetimes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public RouteTime routetime(@PathVariable("id") long id) {
		TravelTimeData travelTimeData = travelTimeService.getTravelTime();
		if (id == 1) {
			return new RouteTime(id, "Asker - Lysaker", Arrays.asList(travelTimeData.getLeg(100098),
					travelTimeData.getLeg(100159), travelTimeData.getLeg(100160)));
		} else if (id == 2) {
			return new RouteTime(id, "Lysaker - Asker", Arrays.asList(travelTimeData.getLeg(100161),
					travelTimeData.getLeg(100162), travelTimeData.getLeg(100101)));
		} else {
			return null;
		}

	}
}
