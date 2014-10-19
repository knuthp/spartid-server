package com.spartid.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

@Controller
public class MainController {
	private static Log LOG = LogFactory.getLog(MainController.class);
	private DB db;

	@Autowired
	public MainController(DB db) {
		this.db = db;
	}
	
	public MainController() {
		this.db = null;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> home() {
		LOG.info("Handling a request");
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("result", "ok");
		if (db != null) {
			LOG.info("Adding to MongoDB");
			DBCollection coll = db.getCollection("testCollection");
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
					.append("type", "database")
					.append("count", 1)
					.append("info",
							new BasicDBObject("x", 203).append("y", 102));
			coll.insert(doc);
		} else {
			LOG.info("No MongoDB");
		}

		return response;
	}

}
