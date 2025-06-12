/**
 * The parent class for shapes.
 */

package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
/**
 * Abstract class to define drawable Shapes
 */
public abstract class Shape implements Serializable {
    // Coordinates where the shape starts
    protected double x;
    protected double y;

    // Color of the shape, using a serializable wrapper
    protected SerializableColor color;

    // Outline thickness
    protected int outline;

    // Drawing mode: e.g., "fill" or "outline"
    protected String mode;

    // Whether the shape can be drawn in one step (e.g., Rectangle, Circle)
    protected boolean oneStep = true;

    // Default constructor: leaves attributes uninitialized
    public Shape() {
        // NOT STUB!
        // This is so all attributes are null
    }

    // Constructor initializing starting position
    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Setter for x-coordinate
    public void setX(double x) {
        this.x = x;
    }

    // Setter for y-coordinate
    public void setY(double y) {
        this.y = y;
    }

    // Getter for x-coordinate
    public double getX() {
        return x;
    }

    // Getter for y-coordinate
    public double getY() {
        return y;
    }

    /**
     * Sets the initial starting coordinate for the shape at mouse press (to start)
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void startShape(double x, double y, SerializableColor color, int outline, String mode) {
        setX(x);
        setY(y);
        setColor(color.toColor());
        setOutline(outline);
        setMode(mode);
    }

    /**
     * Updates the shape feedback at mouse drag (to draw/get feedback)
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public abstract void updateSize(double x, double y);

    /**
     * Updates the shape information, especially important for multi-step shapes, at release (to set a part)
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void updateShape(double x, double y) {
        setSize(x, y);
    }

    /**
     * Updates shape information at release/double click (to set)
     * By default, just delegates to `updateSize`
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setSize(double x, double y) {
        updateSize(x, y);
    }

    // Abstract method to render the shape onto the canvas
    public abstract void draw(GraphicsContext g2d, String mode);

    // Setter for the shape's color using a JavaFX Color
    public void setColor(Color color) {
        this.color = new SerializableColor(color);
    }

    // Getter for the shape's JavaFX Color
    public Color getColor() {
        return color.toColor();
    }

    // Setter for drawing mode
    public void setMode(String mode) {
        this.mode = mode;
    }

    // Getter for drawing mode
    public String getMode() {
        return mode;
    }

    // Setter for outline width
    public void setOutline(int outline) {
        this.outline = outline;
    }

    // Getter for outline width
    public int getOutline() {
        return outline;
    }

    // Whether this shape represents a closed polygon
    public boolean isPolygon() {
        return true;
    }
}
