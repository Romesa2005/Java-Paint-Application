package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

/**
 * The CommandInvoker class is responsible for managing and executing commands.
 * It stores a list of Command objects and handles invoking them.
 *
 * This class supports the Command design pattern, helping separate command logic
 * from the object that invokes the command.
 */
public class CommandInvoker {
    // List to store all commands issued
    private ArrayList<Command> commands;

    /**
     * Constructor initializes the commands list.
     */
    public CommandInvoker() {
        commands = new ArrayList<Command>();
    }

    /**
     * Executes a new command.
     * The new command is added to the list, the first command in the list is removed and executed.
     *
     * @param command The command to be executed.
     */
    public void executeCommand(Command command) {
        commands.addLast(command); // Add the new command to the end of the list
        commands.removeFirst().execute(); // Remove and execute the first command in the list
    }
}
