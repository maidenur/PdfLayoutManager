// Copyright 2014-08-20 PlanBase Inc. & Glen Peterson
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.planbase.pdf.layoutmanager;

/**
 Represents a 2D coordinate in terms of X and Y where negative y is down from the upper left-hand
 corner.  Do not confuse this with an XyDim which represents positive width and height.
 */
public class XyOffset {
    public static final XyOffset ORIGIN = new XyOffset(0, 0) {
        @Override public int hashCode() { return 0; }
    };
    private final double x;
    private final double y;
    private XyOffset(double xCoord, double yCoord) { x = xCoord; y = yCoord; }
    public static XyOffset of(double x, double y) {
        if ((x == 0) && (y == 0)) { return ORIGIN; }
        return new XyOffset(x, y);
    }
    public double x() { return x; }
    public double y() { return y; }
    public XyOffset x(double newX) { return of(newX, y); }
    public XyOffset y(double newY) { return of(x, newY); }

//    public XyOffset minus(XyOffset that) { return of(this.x - that.x(), this.y - that.y()); }
//    public XyOffset plus(XyOffset that) { return of(this.x + that.x(), this.y + that.y()); }

    public XyOffset plusXMinusY(XyOffset that) { return of(this.x + that.x(), this.y - that.y()); }

//    public XyOffset maxXandY(XyOffset that) {
//        if ((this.x >= that.x()) && (this.y >= that.y())) { return this; }
//        if ((this.x <= that.x()) && (this.y <= that.y())) { return that; }
//        return of((this.x > that.x()) ? this.x : that.x(),
//                  (this.y > that.y()) ? this.y : that.y());
//    }
    public XyOffset maxXMinY(XyOffset that) {
        if ((this.x >= that.x()) && (this.y <= that.y())) { return this; }
        if ((this.x <= that.x()) && (this.y >= that.y())) { return that; }
        return of((this.x > that.x()) ? this.x : that.x(),
                  (this.y < that.y()) ? this.y : that.y());
    }

    /** Compares dimensions */
    public boolean lte(XyOffset that) { return (this.x <= that.x()) && (this.y >= that.y()); }

    @Override public String toString() { return "XyOffset(" + x + " " + y + ")"; }

    @Override public int hashCode() { return Double.hashCode(x) ^ Double.hashCode(y); }

    @Override public boolean equals(Object other) {
        // Cheapest operation first...
        if (this == other) { return true; }

        if ( (other == null) ||
             !(other instanceof XyOffset) ||
             (this.hashCode() != other.hashCode()) ) {
            return false;
        }
        // Details...
        final XyOffset that = (XyOffset) other;

        // Compare "significant" fields here.
        return (x == that.x()) && (y == that.y());
    }
}
