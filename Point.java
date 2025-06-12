package ca.utoronto.utm.assignment2.paint;

import java.io.Serializable;

public class Point implements Serializable {
        double x, y; // Coordinates of the point, accessible within the package

        Point() {
                // Default constructor (does nothing but required for serialization or initialization)
        }

        Point(double x, double y){
                this.x = x; this.y = y; // Constructor to initialize point with given coordinates
        }
}
