package com.spartid.server.geoconversion;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Component;

@Component
public class UtmToLatLong {

    private CoordinateReferenceSystem utmZone33NRef;
    private CoordinateReferenceSystem wgs84Ref;
    private MathTransform mathTransform;

    public UtmToLatLong() throws GeoConversionException {
        try {
            utmZone33NRef = CRS.decode("EPSG:32633");
            wgs84Ref = CRS.decode("EPSG:4326");
            boolean lenient = true;
            mathTransform = CRS.findMathTransform(utmZone33NRef, wgs84Ref, lenient);
        } catch (FactoryException e) {
            throw new GeoConversionException(e);
        }
    }

    public GeoLocation2D convertFromUtmToWgs84(UtmPosition utm) throws GeoConversionException {
        DirectPosition2D srcDirectPosition2D = new DirectPosition2D(utmZone33NRef, utm.getX(), utm.getY());
        DirectPosition2D destDirectPosition2D = new DirectPosition2D();
        try {
            mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);
            return new GeoLocation2D(destDirectPosition2D.x, destDirectPosition2D.y);
        } catch (MismatchedDimensionException | TransformException e) {
            throw new GeoConversionException(e);
        }

    }
}
