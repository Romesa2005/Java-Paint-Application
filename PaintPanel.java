package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
public class PaintPanel extends Pane implements EventHandler<MouseEvent>, Observer {
    private PaintModel model;                // The model holding all shapes and paint data
    private View view;                       // Reference to the main view for UI interactions
    private ShapeChooserPanel shapeChooserPanel; // Panel for choosing shapes to draw
    public Shape shape = null;               // The current shape being drawn
    private Color color = Color.BLACK;       // Current drawing color (default black)
    private String mode = "";                // Current mode (e.g., draw, select)
    private int outline;                    // Outline thickness for shapes
    private Canvas canvas;                  // Canvas to draw on

    // Constructor initializes the panel with model, shape chooser and view
    public PaintPanel(PaintModel model, ShapeChooserPanel shapeChooserPanel, View view) {
        this.canvas = new Canvas(300, 300);  // Create canvas of fixed size
        this.model=model;
        this.view=view;
        this.shapeChooserPanel = shapeChooserPanel;
        this.model.addObserver(this);       // Register as observer to model changes

        // Add mouse event handlers to this panel
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this.mousePressed);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this.mouseReleased);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this.mouseMoved);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.mouseClicked);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this.mouseDragged);

        this.getChildren().add(canvas);    // Add canvas to the pane
    }

    // Handle mouse press events
    private EventHandler<MouseEvent> mousePressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mode ==""){return;}            // If no mode selected, ignore
            if (mode != ""){
                if (shape == null ) {          // If no current shape, start new shape
                    shape = shapeChooserPanel.getSelectedShape();
                    shape.startShape(mouseEvent.getX(), mouseEvent.getY(), new SerializableColor(color), outline, mode);
                    if (shape instanceof Textbox) {
                        ((Textbox) shape).addText(view, model);  // Special handling for Textbox
                    }
                } else {
                    if (mouseEvent.getClickCount() == 2) {    // Double-click completes shape
                        shape.setSize(mouseEvent.getX(), mouseEvent.getY());
                        model.addShape(shape);      // Add completed shape to model
                        shape = null;              // Reset current shape
                    } else {
                        shape.updateSize(mouseEvent.getX(), mouseEvent.getY()); // Update size on single click
                    }
                }
            }
            else if (shapeChooserPanel.getSelectedShape().getClass() == Squiggle.class){
                if (shape == null ) {          // Special case for Squiggle shape start
                    shape = shapeChooserPanel.getSelectedShape();
                    shape.startShape(mouseEvent.getX(), mouseEvent.getY(),  new SerializableColor(color), outline, mode);
                }
            }
            update1();  // Redraw canvas after changes
        }
    };

    // Handle mouse drag events to update shape size dynamically
    private EventHandler<MouseEvent> mouseDragged = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(shape != null){
                shape.updateSize(mouseEvent.getX(), mouseEvent.getY());  // Update shape size as mouse drags
                update1();      // Redraw canvas
            }
        }
    };

    // Handle mouse release events to finalize shape if applicable
    private EventHandler<MouseEvent> mouseReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (shape != null) {
                if (shape.oneStep) {     // If shape needs only one step to finish
                    shape.updateShape(mouseEvent.getX(), mouseEvent.getY());
                    model.addShape(shape);  // Add shape to model
                    shape = null;          // Reset current shape
                } else {
                    shape.updateShape(mouseEvent.getX(), mouseEvent.getY()); // Update shape on release
                    if (shape.isPolygon()){
                        model.addShape(shape);  // Add polygon shape to model on release
                        shape = null;
                    }
                }
                update1();  // Final redraw after release
            }
        }
    };

    // Empty handler for mouse move events (could be used for cursor updates)
    private EventHandler<MouseEvent> mouseMoved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            // Currently no action on mouse moved
        }
    };

    // Empty handler for mouse click events (separate from press/release)
    private EventHandler<MouseEvent> mouseClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            // Currently no action on mouse clicked
        }
    };

    // Implementation of EventHandler interface, required but unused here
    @Override
    public void handle(MouseEvent mouseEvent) {
        // NOT STUB!
        // Need this or java throws a tantrum
    }

    // Setter for current drawing color
    public void setColor(Color color) {
        this.color = color;
    }

    // Setter for current drawing mode
    public void setMode(String mode) {
        this.mode = mode;
    }

    // Setter for outline thickness
    public void setOutline(int outline) {
        this.outline = outline;
    }

    // Setter for model reference
    public void setModel(PaintModel model) {
        this.model = model;
    }

    // Setter for current shape being drawn
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    // Method to redraw the canvas with current shapes and the active shape (feedback)
    public void update1() {
        GraphicsContext g2d = canvas.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());  // Clear canvas

        ArrayList<Shape> shapes = this.model.getShapes();  // Get all shapes from model
        for (Shape shape : shapes) {
            shape.draw(g2d, shape.getMode());              // Draw each shape
        }

        // Draw currently active shape for feedback
        if (this.shape != null) {
            shape.draw(g2d, mode);
        }
    }

    // Called when the observed model changes, triggers a redraw
    @Override
    public void update(Observable o, Object arg) {
        update1();
    }
}
