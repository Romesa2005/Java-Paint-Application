package ca.utoronto.utm.assignment2.scribble;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Main class to launch the Scribble application.
 * Sets up the primary stage and adds a custom ScribblePanel to the scene.
 */
public class Scribble extends Application {

        /**
         * Entry point for the JavaFX application.
         * Called automatically when the application is launched.
         *
         * @param stage The primary window of the application.
         */
        @Override
        public void start(Stage stage) throws Exception {
                // Create a custom panel for drawing or scribbling
                ScribblePanel scribblePanel = new ScribblePanel();

                // Create an HBox layout and add padding
                HBox root = new HBox();
                root.setPadding(new Insets(5));

                // Add the drawing panel to the layout
                root.getChildren().add(scribblePanel);

                // Create a scene with the layout as the root node
                Scene scene = new Scene(root);

                // Set window title and attach scene to stage
                stage.setTitle("Scribble");
                stage.setScene(scene);

                // Display the stage
                stage.show();
        }

        /**
         * The main method that launches the JavaFX application.
         * This method starts the GUI thread and invokes the `start()` method.
         */
        public static void main(String[] args) {
                launch(args);  // Calls Application.start() internally
        }
}
