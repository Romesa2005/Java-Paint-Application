/**
 * A class for Squares.
 */

package ca.utoronto.utm.assignment2.paint;
/**
 * Square Shape which allows users to add a square to the Canvas
 */
public class Square extends Rectangle {

    // Default constructor
    public Square() {}

    // Constructor initializing the top-left corner position (x, y)
    public Square(int x, int y) {
        super(x, y);
    }

    // Constructor initializing position (x, y) and size (width, height)
    // Note: width and height are forced to be equal by passing width twice
    public Square(int x, int y, int width, int height) {
        super(x, y, width, width); // Ensure width == height for a square
    }

    /**
     * Overrides setWidth to ensure width and length remain equal for a square
     * @param width the new width (and length) of the square
     */
    public void setWidth(double width) {
        super.setWidth(width);
        super.setLength(width); // Keep length equal to width
    }

    /**
     * Overrides setLength to ensure width and length remain equal for a square
     * @param length the new length (and width) of the square
     */
    public void setLength(double length) {
        super.setWidth(length);  // Keep width equal to length
        super.setLength(length);
    }

    /**
     * Updates the size of the square based on new canvas coordinates.
     * Calls the Rectangle's updateSize, then adjusts width and length to keep them equal.
     * @param canvas_x x coordinate of canvas update
     * @param canvas_y y coordinate of canvas update
     */
    public void updateSize(double canvas_x, double canvas_y) {
        super.updateSize(canvas_x, canvas_y);
        // Adjust width and length to be the max absolute of both dimensions to maintain square shape
        if (Math.abs(length) > Math.abs(width)) {
            width = length;
        } else {
            length = width;
        }
    }
}
