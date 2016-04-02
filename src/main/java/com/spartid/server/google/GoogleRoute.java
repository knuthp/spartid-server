package com.spartid.server.google;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.spartid.server.geoconversion.GeoLocation2D;

public class GoogleRoute {
    private final String from;
    private final String to;

    public GoogleRoute(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public GoogleRoute(GeoLocation2D start, GeoLocation2D end) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        NumberFormat formatter = new DecimalFormat("#0.0000000", otherSymbols);
        from = formatter.format(start.getLat()) + "," + formatter.format(start.getLong());
        to = formatter.format(end.getLat()) + "," + formatter.format(end.getLong());
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
