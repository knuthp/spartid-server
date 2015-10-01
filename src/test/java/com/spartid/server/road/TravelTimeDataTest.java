package com.spartid.server.road;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class TravelTimeDataTest {

	private static final String NOR_REAL_OK_FILE = "src/test/resources/GetTravelTimeData.xml";

	@Test
	public void testParsePublicationTime() throws Exception {
		TravelTimeData travelTimeData = parseFile(NOR_REAL_OK_FILE);

		assertThat(travelTimeData.getPublicationTime(),
				equalTo("2015-10-01T20:25:31+02:00"));
		assertThat(travelTimeData.getLegsTravelTime().size(), equalTo(156));
	}

	@Test
	public void testParseLegs() throws Exception {
		TravelTimeData travelTimeData = parseFile(NOR_REAL_OK_FILE);

		assertThat(travelTimeData.getLegsTravelTime().size(), equalTo(156));
	}

	@Test
	public void testParseLegContent() throws Exception {
		TravelTimeData travelTimeData = parseFile(NOR_REAL_OK_FILE);

		LegTravelTime leg = travelTimeData.getLegsTravelTime().get(0);
		assertThat(leg.getId(), equalTo(100193L));
		assertThat(leg.getTravelTimeTrendType(),
				equalTo(TravelTimeTrendType.STABLE));
		assertThat(leg.getTravelTimeType(), equalTo(TravelTimeType.ESTIMATED));
		assertThat(leg.getTravelTime(), equalTo(610.0));
		assertThat(leg.getFreeFlowTime(), equalTo(573.0));
	}

	private TravelTimeData parseFile(String filePath) throws JAXBException {
		File file = new File(filePath);
		JAXBContext jaxbContext = JAXBContext.newInstance(TravelTimeData.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		TravelTimeData travelTimeData = (TravelTimeData) jaxbUnmarshaller
				.unmarshal(file);
		return travelTimeData;
	}

}
