package com.spartid.server.geoconversion;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.math.DoubleMath;

public class UtmPosition {
    private static final double TOLERANCE_EQUALS = 1.0;
    private final double x;
    private final double y;

    public UtmPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                x,
                y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UtmPosition)) {
            return false;
        }
        UtmPosition other = (UtmPosition) obj;
        return DoubleMath.fuzzyEquals(x, other.x, TOLERANCE_EQUALS)
                && DoubleMath.fuzzyEquals(y, other.y, TOLERANCE_EQUALS);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", x)
                .add("y", y)
                .toString();
    }
}
