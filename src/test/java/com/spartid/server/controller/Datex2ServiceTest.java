package com.spartid.server.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.json.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spartid.server.road.TravelTimeForLeg;

public class Datex2ServiceTest {

	@Test
	public void testCanParseXmlAndGetJson() throws Exception {
		Datex2Service service = new Datex2Service(new URL(
				"file://src/test/resources/elaborated_data_travel_times.xml"));

		JSONObject json = service.getTravelTimes();

		assertThat(
				json.getJSONObject("d2LogicalModel").getJSONObject("exchange")
						.getJSONObject("supplierIdentification")
						.getString("country"), equalTo("se"));
	}

	@Test
	public void testCanConvertLeg() throws Exception {
		Datex2Service service = new Datex2Service(new URL(
				"file://src/test/resources/elaborated_data_travel_times.xml"));
		JSONObject json = service.getTravelTimes();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		// objectMapper.configure(DeserializationFeature, state)
		String travelTimeForLegJson = json.getJSONObject("d2LogicalModel")
				.getJSONObject("payloadPublication")
				.getJSONArray("elaboratedData").getJSONObject(0).toString();
		TravelTimeForLeg travelTimeForLeg = objectMapper.readValue(
				travelTimeForLegJson, TravelTimeForLeg.class);

		System.out.println(travelTimeForLegJson);
		System.out.println(objectMapper.writeValueAsString(travelTimeForLeg));
		assertThat(travelTimeForLeg.getId(),
				equalTo("SE_SRA_ELABORATEDDATA_GBG107108"));
		assertThat(travelTimeForLeg.getTravelTime(), equalTo(33L));
	}
}
