package com.spartid.server.road;

import java.util.ArrayList;
import java.util.List;

import com.spartid.server.geoconversion.UtmPosition;

public class LinearCoordinates extends ArrayList<UtmPosition> implements List<UtmPosition> {
    private static final long serialVersionUID = 1L;

    public UtmPosition start() {
        if (isEmpty()) {
            throw new IllegalStateException("Collection of coordinates is empty");
        }
        return this.get(0);
    }

    public UtmPosition end() {
        if (isEmpty()) {
            throw new IllegalStateException("Collection of coordinates is empty");
        }
        return this.get(this.size() - 1);
    }
}
