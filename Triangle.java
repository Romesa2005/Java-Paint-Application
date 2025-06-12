package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Triangle Shape which allows users to add a triangle to the Canvas.
 * The triangle is defined by three points and can be drawn either filled or outlined.
 */
public class Triangle extends Shape {

    // Coordinates for the second and third points of the triangle
    protected double point2X;
    protected double point2Y;
    protected double point3X;
    protected double point3Y;

    // Default constructor
    public Triangle() {}

    /**
     * Constructs a triangle using three points.
     */
    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        x = x1;              // First point (also stored in the base Shape)
        y = y1;
        point2X = x2;        // Second point
        point2Y = y2;
        point3X = x3;        // Third point
        point3Y = y3;
    }

    /**
     * Sets the x-coordinate for the first point and aligns other points to it.
     */
    public void setX(double x) {
        super.setX(x);
        point2X = x;
        point3X = x;
    }

    /**
     * Sets the y-coordinate for the first point and aligns other points to it.
     */
    public void setY(double y) {
        super.setY(y);
        point2Y = y;
        point3Y = y;
    }

    /**
     * Updates the size of the triangle based on the given canvas coordinates.
     * This method adjusts the second and third points dynamically as the user drags.
     */
    public void updateSize(double canvas_x, double canvas_y) {
        point2X = canvas_x;
        point2Y = canvas_y;

        // Third point is halfway horizontally, same vertical as point2
        point3X = canvas_x / 2;
        point3Y = canvas_y;
    }

    /**
     * Draws the triangle on the canvas.
     * If mode is "fill", the triangle is filled with color.
     * If mode is "outline", the triangle is drawn with an outline only.
     */
    @Override
    public void draw(GraphicsContext g2d, String mode) {
        // Arrays of x and y coordinates for the triangle points
        double[] infoX = {x, point2X, point3X, x};
        double[] infoY = {y, point2Y, point3Y, y};

        if (mode.equals("fill")) {
            g2d.setFill(this.color.toColor());
            g2d.fillPolygon(infoX, infoY, 3);  // Draw filled triangle
        } else if (mode.equals("outline")) {
            g2d.setLineWidth(this.outline);
            g2d.setStroke(this.color.toColor());
            g2d.strokePolygon(infoX, infoY, 3);  // Draw triangle outline
        }
    }
}
