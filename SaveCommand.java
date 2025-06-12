package ca.utoronto.utm.assignment2.paint;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * Save Command that allows the user to save the state of the current PaintModel
 * as a .paint file
 */
public class SaveCommand implements Command {
    protected View view;           // Reference to the application view
    protected PaintModel paintModel; // The model containing all the drawn shapes
    protected Stage stage;         // JavaFX stage used for showing the file dialog

    // Constructor to initialize SaveCommand with required components
    public SaveCommand(View view, PaintModel model, Stage stage) {
        this.view = view;
        this.paintModel = model;
        this.stage = stage;
    }

    // Executes the save operation
    @Override
    public void execute() {
        try {
            // Create a file chooser dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Project");

            // Limit the file types to only ".paint" files
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Paint Model Files", "*.paint")
            );

            // Show the Save dialog and capture the file location
            File file = fileChooser.showSaveDialog(stage);

            // If the user chose a location, attempt to serialize the model into the file
            if (file != null) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(paintModel); // Serialize and save the paint model
                } catch (IOException e) {
                    throw new RuntimeException(e); // Rethrow as unchecked if an I/O error occurs
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e); // Catch and rethrow runtime exceptions
        }
    }
}
