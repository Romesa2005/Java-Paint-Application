/**
 * A class for Circles.
 */

package ca.utoronto.utm.assignment2.paint;

/**
 * Circle Shape which allows users to add a circle to the Canvas.
 * Inherits from the Oval class to reuse functionality.
 */
public class Circle extends Oval {

        /**
         * Default constructor.
         */
        public Circle() {}

        /**
         * Constructor that initializes the circle's position.
         * @param x the x-coordinate of the circle
         * @param y the y-coordinate of the circle
         */
        public Circle(double x, double y) {
                super(x, y);
        }

        /**
         * Constructor that initializes the circle with position and size.
         * Ensures the width and height are equal to maintain a circular shape.
         * @param x the x-coordinate
         * @param y the y-coordinate
         * @param width the width of the circle
         * @param height the height (ignored, width used for both dimensions)
         */
        public Circle(double x, double y, double width, double height) {
                super(x, y, width, width); // Force height = width to ensure circle
        }

        /**
         * Overrides the setWidth method to ensure width and height stay equal.
         * @param width the new width
         */
        public void setWidth(double width) {
                super.setWidth(width);
                super.setLength(width); // Ensures it's a circle
        }

        /**
         * Overrides the setLength method to ensure width and height stay equal.
         * @param length the new length
         */
        public void setLength(double length) {
                super.setWidth(length);
                super.setLength(length); // Ensures it's a circle
        }

        /**
         * Updates the size of the circle based on new canvas coordinates.
         * Ensures the shape remains a circle by equalizing width and length.
         * @param canvas_x the new x-coordinate
         * @param canvas_y the new y-coordinate
         */
        public void updateSize(double canvas_x, double canvas_y) {
                super.updateSize(canvas_x, canvas_y);
                if (Math.abs(length) > Math.abs(width)) {
                        width = length; // Match width to larger dimension
                } else if (mode.equals("outline")) {
                        length = width; // Match length to width for outlines
                }
        }
}
