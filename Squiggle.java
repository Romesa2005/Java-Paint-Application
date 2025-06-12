package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Squiggle Shape which allows users to draw on the Canvas with their mouse
 */
public class Squiggle extends Shape {
    // List of points that make up the squiggle path
    protected ArrayList<Point> points = new ArrayList<Point>();

    // Default constructor
    public Squiggle() {}

    // Constructor that initializes the squiggle with a starting point
    public Squiggle(double x, double y) {
        points.add(new Point(x, y));
    }

    /**
     * Initializes the shape with the given starting coordinates, color,
     * outline width, and mode. Also adds the first point to the squiggle.
     */
    public void startShape(double x, double y, SerializableColor color, int outline, String mode) {
        super.startShape(x, y, color, outline, mode);
        points.add(new Point(x, y));
    }

    /**
     * Adds a new point to the squiggle whenever the mouse moves.
     * This method is repeatedly called as the mouse is dragged.
     */
    @Override
    public void updateSize(double x, double y) {
        points.add(new Point(x, y));
    }

    /**
     * Converts the list of points into a flat array of doubles
     * representing the x and y coordinates alternately.
     * Used to facilitate drawing the squiggle.
     */
    protected double[] getDrawingInfo() {
        int size = points.size() * 2;
        double[] info = new double[size];
        for (int i = 0; i < size; i += 2) {
            info[i] = points.get(i / 2).x;
            info[i + 1] = points.get(i / 2).y;
        }
        return info;
    }

    /**
     * Draws the squiggle on the canvas by connecting each pair of consecutive points
     * with a line segment.
     */
    @Override
    public void draw(GraphicsContext g2d, String mode) {
        double[] info = getDrawingInfo();
        g2d.setStroke(this.color.toColor());
        g2d.setLineWidth(this.outline);
        for (int i = 0; i < info.length - 2; i += 2) {
            g2d.strokeLine(info[i], info[i + 1], info[i + 2], info[i + 3]);
        }
    }
}
