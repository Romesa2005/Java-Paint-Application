package ca.utoronto.utm.assignment2.paint;

/**
 * The Command interface follows the Command design pattern.
 * It defines a single method, execute(), that all concrete command classes must implement.
 *
 * This pattern allows encapsulating a request as an object, letting you parameterize clients
 * with queues, requests, and operations. It's especially useful for undo/redo functionality
 * or for handling actions in GUI applications.
 */
public interface Command {
    /**
     * Execute the action associated with this command.
     */
    public abstract void execute();
}
