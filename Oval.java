/**
 * A class for Ovals.
 */

package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Oval Shape which allows users to add an oval to the Canvas.
 * This class extends the Shape superclass and provides properties
 * and methods specific to oval shapes, including drawing logic.
 */

public class Oval extends Shape {
    // Width and length (height) of the oval
    protected double width;
    protected double length;

    /**
     * Default constructor.
     */
    public Oval() {}

    /**
     * Constructor to initialize an oval at a specific position with zero width and length.
     *
     * @param x The x-coordinate of the oval's center
     * @param y The y-coordinate of the oval's center
     */
    public Oval(double x, double y) {
        super(x, y);
        this.width = 0;
        this.length = 0;
    }

    /**
     * Constructor to initialize an oval at a specific position with given width and length.
     *
     * @param x The x-coordinate of the oval's center
     * @param y The y-coordinate of the oval's center
     * @param width The initial width of the oval
     * @param length The initial length (height) of the oval
     */
    public Oval(double x, double y, double width, double length) {
        super(x, y);
        this.width = width;
        this.length = length;
    }

    // Getters and setters for position and size

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Updates the width and length of the oval based on a new canvas coordinate.
     * This typically reflects how far the user has dragged the mouse.
     *
     * @param canvas_x The x-coordinate on the canvas
     * @param canvas_y The y-coordinate on the canvas
     */
    public void updateSize(double canvas_x, double canvas_y) {
        width = (canvas_x - x) * 2;
        length = (canvas_y - y) * 2;
    }

    /**
     * Calculates the top-left coordinates and dimensions to be used for drawing
     * the oval in a way that handles negative width/length correctly.
     *
     * @return An array containing {x, y, width, length} for drawing
     */
    private double[] getDrawingInfo() {
        double posX = x - (width / 2.0);
        double posY = y - (length / 2.0);
        double posW = width;
        double posL = length;
        double[] info = new double[4];

        // Adjust position and convert negative dimensions to positive
        if (posW < 0) {
            posX = posX + posW;
            posW = Math.abs(posW);
        }
        if (posL < 0) {
            posY = posY + posL;
            posL = Math.abs(posL);
        }

        info[0] = posX;
        info[1] = posY;
        info[2] = posW;
        info[3] = posL;

        return info;
    }

    /**
     * Draws the oval on the canvas using JavaFX's GraphicsContext.
     * Supports both "fill" and "outline" drawing modes.
     *
     * @param g2d The GraphicsContext used to draw on the canvas
     * @param mode Either "fill" or "outline" depending on how the oval should be drawn
     */
    @Override
    public void draw(GraphicsContext g2d, String mode) {
        double[] info = getDrawingInfo();
        if (mode.equals("fill")) {
            g2d.setFill(this.color.toColor());
            g2d.fillOval(info[0], info[1], info[2], info[3]);
        } else if (mode.equals("outline")) {
            g2d.setLineWidth(this.outline);
            g2d.setStroke(this.color.toColor());
            g2d.strokeOval(info[0], info[1], info[2], info[3]);
        }
    }
}
