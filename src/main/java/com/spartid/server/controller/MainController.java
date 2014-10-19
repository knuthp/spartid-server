package com.spartid.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	private static Log LOG = LogFactory.getLog(MainController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> home() {
		LOG.info("Handling a request");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", "ok");
		return response;
	}

}
