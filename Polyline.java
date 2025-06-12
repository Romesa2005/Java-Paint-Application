package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * Polyline Shape which allows users to add connecting lines to the Canvas.
 * A Polyline ends if the user clicks twice.
 */
public class Polyline extends Squiggle {
    protected Point nextPoint; // Holds the next point to be added to the polyline

    public Polyline() {
        oneStep = false; // Polyline is not a one-step shape (requires multiple user actions)
    }

    // Updates the tentative next point to be drawn based on current mouse position
    public void updateSize(double x, double y) {
        // If the cursor is near the starting point and there are enough points, prepare to close the polyline
        if (points.size() > 2 &&
                Math.sqrt(Math.pow((x - points.get(0).x), 2) + Math.pow((y - points.get(0).y), 2)) < 10) {
            nextPoint = points.get(0); // Snap to start point
        }
        // If nextPoint hasn't been initialized, create a new one
        else if (nextPoint == null) {
            nextPoint = new Point(x, y);
            // Debug logs
            System.out.println(Math.pow(2, 8));
            System.out.println(Math.pow(2, -8));
        }
        // If nextPoint exists, just update its coordinates
        else {
            nextPoint.x = x;
            nextPoint.y = y;
        }
    }

    // Finalizes the current segment by adding nextPoint to the polyline
    @Override
    public void updateShape(double x, double y) {
        updateSize(x, y);
        points.add(nextPoint);   // Add the finalized point
        nextPoint = null;        // Reset for the next segment
    }

    // Determines if the polyline forms a closed polygon
    public boolean isPolygon() {
        return points.size() > 2 &&
                points.getFirst().x == points.getLast().x &&
                points.getFirst().y == points.getLast().y;
    }

    // Removes the last added point, usually when closing the shape
    public void setSize(double x, double y) {
        points.remove(points.size() - 1);
    }

    // Draws the polyline or filled polygon depending on its state and mode
    public void draw(GraphicsContext g2d, String mode) {
        double[] info = getDrawingInfo(); // Flattened list of point coordinates
        g2d.setStroke(this.color.toColor());
        g2d.setLineWidth(this.outline);

        // If the polyline is closed and has enough points, treat as polygon
        if (points.size() > 2 &&
                points.getFirst().x == points.getLast().x &&
                points.getFirst().y == points.getLast().y) {
            double[] infoX = new double[points.size()];
            double[] infoY = new double[points.size()];

            // Extract X and Y coordinates separately
            for (int i = 0; i < points.size(); i++) {
                infoX[i] = points.get(i).x;
                infoY[i] = points.get(i).y;
            }

            // Draw filled or outlined polygon
            if (mode.equals("fill")) {
                g2d.setFill(this.color.toColor());
                g2d.fillPolygon(infoX, infoY, points.size());
            } else if (mode.equals("outline")) {
                g2d.setLineWidth(this.outline);
                g2d.setStroke(this.color.toColor());
                g2d.strokePolygon(infoX, infoY, points.size());
            }
        }

        // Draw the lines connecting the points of the polyline
        for (int i = 0; i < info.length - 2; i += 2) {
            g2d.strokeLine(info[i], info[i + 1], info[i + 2], info[i + 3]);
        }

        // Draw a preview line from the last point to nextPoint, if available
        if (nextPoint != null) {
            g2d.strokeLine(info[info.length - 2], info[info.length - 1], nextPoint.x, nextPoint.y);
        }
    }
}
