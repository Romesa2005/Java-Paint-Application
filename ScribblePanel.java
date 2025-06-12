package ca.utoronto.utm.assignment2.scribble;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


/**
 * A custom Canvas that allows users to draw small red dots as the mouse moves across it.
 * Implements basic mouse movement handling to simulate a scribble effect.
 */
public class ScribblePanel extends Canvas implements EventHandler<MouseEvent> {

    /**
     * Constructor initializes the canvas size and adds a mouse movement handler.
     */
    public ScribblePanel() {
        super(200, 200); // Create a canvas of width 200 and height 200
        // Register this panel to handle MOUSE_MOVED events
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
    }

    /**
     * Handles mouse movement by drawing a small red pixel at the cursor location.
     *
     * @param mouseEvent The mouse movement event.
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        // Get the graphics context used for drawing on the canvas
        GraphicsContext gc = this.getGraphicsContext2D();

        // Get the current mouse position
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        // Set the fill color to red and draw a 1x1 rectangle at the mouse position
        gc.setFill(Color.RED);
        gc.fillRect(x, y, 1, 1); // This creates the "scribble" effect
    }
}
