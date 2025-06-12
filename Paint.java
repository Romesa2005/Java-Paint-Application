package ca.utoronto.utm.assignment2.paint;


import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Main application class for the Paint program.
 * Follows the MVC (Model-View-Controller) pattern using JavaFX.
 *
 * - `PaintModel` represents the model (data and logic).
 * - `View` acts as both the view and controller (UI and event handling).
 */

public class Paint extends Application {

        // The model containing shape data and logic
        PaintModel model;

        // The view and controller for the UI
        View view;

        /**
         * The main entry point of the application.
         * Calls JavaFX's launch to start the app lifecycle.
         *
         * @param args command-line arguments (unused here)
         */
        public static void main(String[] args) {
                launch(args);
        }

        /**
         * This method is called by the JavaFX framework after application launch.
         * Initializes the model and the main view.
         *
         * @param stage The primary stage (window) provided by JavaFX
         * @throws Exception if there is any error during initialization
         */
        @Override
        public void start(Stage stage) throws Exception {
                this.model = new PaintModel(); // Initialize the model

                // Create the main view and controller, passing model and stage
                this.view = new View(model, stage, this);
        }

        /**
         * Launches a new window with a fresh model and view.
         * Called when the user selects "New" to start over.
         *
         * @throws Exception if there is an error creating the new view
         */
        public void newView() throws Exception {
                Stage stage = new Stage(); // Create a new window
                this.model = new PaintModel(); // Reset the model
                this.view = new View(model, stage, this); // Launch a new view/controller
        }
}
