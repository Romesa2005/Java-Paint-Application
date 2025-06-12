package ca.utoronto.utm.assignment2.paint;

import javafx.stage.Stage;

/**
 * NewCommand is a concrete implementation of the Command interface.
 *
 * When executed, this command closes the current application window (Stage)
 * and opens a new view using the Paint application logic.
 *
 * This is part of the Command design pattern, allowing actions to be encapsulated
 * as objects that can be executed, queued, or logged.
 */
public class NewCommand implements Command {
    // The current window (stage) that will be closed
    protected Stage stage;

    // Reference to the Paint application, used to open a new view
    protected Paint paintApp;

    /**
     * Constructor to initialize the command with the current stage and Paint application.
     *
     * @param stage The current window to be closed
     * @param paintApp The Paint app instance used to create a new view
     */
    public NewCommand(Stage stage, Paint paintApp) {
        this.stage = stage;
        this.paintApp = paintApp;
    }

    /**
     * Executes the command:
     * - Closes the current stage
     * - Opens a new view using the Paint app
     */
    @Override
    public void execute() {
        try {
            stage.close();          // Close the current window
            paintApp.newView();     // Launch a new view
        } catch (Exception e) {
            throw new RuntimeException(e); // Rethrow exceptions as unchecked
        }
    }
}
