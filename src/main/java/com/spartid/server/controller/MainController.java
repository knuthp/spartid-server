package com.spartid.server.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

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
			DBCollection coll = db.getCollection("reisetiderRT");
			String url = "http://www.reisetider.no/xml/reisetider.xml";
			String jsonString = getUrlAndConvertToJson(url);
			DBObject dbObj  = (DBObject) JSON.parse(jsonString);
			coll.insert(dbObj);
		} else {
			LOG.info("No MongoDB");
		}

		return response;
	}

	
	
	
	private String getUrlAndConvertToJson(String url) {
		LOG.info("Handling xml to json CORS : " + url);
		try {
			InputStream in = new URL(
					url).openStream();
			String xml = IOUtils.toString(in);
			JSONObject xmlJSONObj = XML.toJSONObject(xml);
			return xmlJSONObj.toString(4);
		} catch (MalformedURLException e) {
			LOG.error("Trouble with url: " + url, e);
		} catch (IOException e) {
			LOG.error("Trouble with IO: " + url, e);
		}
		return "";
	}


}
