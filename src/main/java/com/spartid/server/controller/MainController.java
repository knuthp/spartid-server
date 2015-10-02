package com.spartid.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spartid.server.road.TravelTimeData;
import com.spartid.server.road.TravelTimeService;

@Controller
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

    @RequestMapping(value = "/reisetid", method = RequestMethod.GET)
    @ResponseBody
    public TravelTimeData reisetid() {
        LOG.info("Handling a request");
        return travelTimeService.getTravelTime();
    }

}
