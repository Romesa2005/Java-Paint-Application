package ca.utoronto.utm.assignment2.paint;

/**
 * Undo command that removes the most recently drawn shape from the Canvas.
 * Implements the Command design pattern to encapsulate the undo operation.
 */
public class UndoCommand implements Command {

    // Reference to the model which maintains the state of the canvas
    protected PaintModel model;

    // Default constructor (may be used for delayed initialization)
    public UndoCommand() {}

    /**
     * Constructs the command with a reference to the PaintModel.
     * @param model The PaintModel that stores shapes and handles undo.
     */
    public UndoCommand(PaintModel model) {
        this.model = model;
    }

    /**
     * Executes the undo operation by calling the model's undo method.
     */
    public void execute() {
        model.undo();
    }
}
