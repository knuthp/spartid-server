package com.spartid.server.geoconversion;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class UtmToLatLongTest {

    @Test
    public void test() throws GeoConversionException {
        UtmToLatLong utmToLatLong = new UtmToLatLong();

        GeoLocation2D asker = utmToLatLong.convertFromUtmToWgs84(new UtmPosition(238690.0, 6640481.0));

        assertThat(asker.getLat(), closeTo(59.8, 1.0));
        assertThat(asker.getLong(), closeTo(10.3, 1.0));
    }
}
