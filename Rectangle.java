/**
 * A class for Rectangles.
 */

package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Rectangle Shape which allows users to add a rectangle to the Canvas.
 */
public class Rectangle extends Shape {
    protected double width;  // Width of the rectangle
    protected double length; // Height (length) of the rectangle

    // Default constructor
    public Rectangle() {}

    // Constructor initializing only position (x, y)
    public Rectangle(double x, double y) {
        super(x, y);
        this.width = 0;
        this.length = 0;
    }

    // Constructor initializing position and dimensions
    public Rectangle(double x, double y, double width, double length) {
        super(x, y);
        this.width = width;
        this.length = length;
    }

    // Getter for width
    public double getWidth() {
        return width;
    }

    // Getter for length
    public double getLength() {
        return length;
    }

    // Setter for width
    public void setWidth(double width) {
        this.width = width;
    }

    // Setter for length
    public void setLength(double length) {
        this.length = length;
    }

    // Updates the rectangle's width and length based on current mouse position
    public void updateSize(double canvas_x, double canvas_y) {
        width = canvas_x - x;
        length = canvas_y - y;
    }

    // Returns properly adjusted position and dimensions for drawing
    private double[] getDrawingInfo() {
        double posX = x;
        double posY = y;
        double posW = width;
        double posL = length;
        double[] info = new double[4];

        // Adjust for negative width by shifting the x-position
        if (posW < 0) {
            posX = posX + posW;
            posW = Math.abs(posW);
        }

        // Adjust for negative length by shifting the y-position
        if (posL < 0) {
            posY = posY + posL;
            posL = Math.abs(posL);
        }

        info[0] = posX;  // x-coordinate
        info[1] = posY;  // y-coordinate
        info[2] = posW;  // adjusted width
        info[3] = posL;  // adjusted length

        return info;
    }

    // Draws the rectangle on the canvas using GraphicsContext
    @Override
    public void draw(GraphicsContext g2d, String mode) {
        double[] info = getDrawingInfo();

        if (mode.equals("fill")) {
            g2d.setFill(this.color.toColor());
            g2d.fillRect(info[0], info[1], info[2], info[3]); // Filled rectangle
        } else if (mode.equals("outline")) {
            g2d.setLineWidth(this.outline);
            g2d.setStroke(this.color.toColor());
            g2d.strokeRect(info[0], info[1], info[2], info[3]); // Outlined rectangle
        }
    }
}
