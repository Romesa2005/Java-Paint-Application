package ca.utoronto.utm.assignment2.paint;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * Open Command that allows the user to choose a paint file to display on the
 * Canvas.
 */

public class OpenCommand implements Command {
    protected Stage stage;
    protected PaintPanel paintPanel;
    protected PaintModel paintModel;

    public OpenCommand(Stage stage, PaintModel paintModel, PaintPanel paintPanel) {
        this.stage = stage;
        this.paintModel = paintModel;
        this.paintPanel = paintPanel;
    }

    @Override
    public void execute() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Project");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Paint Model Files", "*.paint"));
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    PaintModel selectedModel = (PaintModel) ois.readObject();
                    paintModel = selectedModel;
                    paintPanel.setModel(selectedModel);
                    paintPanel.update(null, null);
                    paintPanel.setMode("");

                } catch (ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
