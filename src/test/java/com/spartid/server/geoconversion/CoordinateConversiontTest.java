package com.spartid.server.geoconversion;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.junit.Before;
import org.junit.Test;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

public class CoordinateConversiontTest {

    private CoordinateReferenceSystem utmZone33NRef;
    private CoordinateReferenceSystem wgs84Ref;

    @Test
    public void test() throws Exception {
        // Asker
        DirectPosition2D srcDirectPosition2D = new DirectPosition2D(utmZone33NRef, 238690.0, 6640481.0);
        boolean lenient = true;
        MathTransform mathTransform = CRS.findMathTransform(utmZone33NRef, wgs84Ref, lenient);

        DirectPosition2D destDirectPosition2D = new DirectPosition2D();
        mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);

        assertThat(destDirectPosition2D.x, closeTo(59.8, 1.0));
        assertThat(destDirectPosition2D.y, closeTo(10.3, 1.0));
    }

    @Before
    public void setUp() throws NoSuchAuthorityCodeException, FactoryException {
        utmZone33NRef = CRS.decode("EPSG:32633");
        wgs84Ref = CRS.decode("EPSG:4326");
    }

}
