package com.spartid.server.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Datex2Service {

	private static final Logger LOG = LoggerFactory
			.getLogger(Datex2Service.class);
	private URL url;

	public Datex2Service(URL url) {
		this.url = url;

	}

	public JSONObject getTravelTimes() {
		try {
			// InputStream in = url.openStream();
			// String xml = IOUtils.toString(in);
			String xml = FileUtils.readFileToString(new File(
					"src/test/resources/elaborated_data_travel_times.xml"));
			return XML.toJSONObject(xml);
		} catch (MalformedURLException e) {
			LOG.error("Trouble with url: " + url, e);
		} catch (IOException e) {
			LOG.error("Trouble with IO: " + url, e);
		}
		return null;
	}
}
