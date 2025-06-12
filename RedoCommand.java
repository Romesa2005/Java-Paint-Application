package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
/**
 * Redo Command that adds the shape removed by the Undo Command back onto the Canvas.
 */
public class RedoCommand implements Command {
    protected PaintModel model; // Reference to the PaintModel to apply redo operation

    // Default constructor
    public RedoCommand() {}

    // Constructor that takes the model to operate on
    public RedoCommand(PaintModel model) {
        this.model = model;
    }

    // Executes the redo action on the model
    public void execute() {
        model.redo();
    }
}
