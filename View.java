package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * The View class sets up the user interface for the paint application.
 * It connects the model and controller parts, handles UI layout, and processes menu events.
 */
public class View implements EventHandler<ActionEvent> {

        // Core components
        private PaintModel paintModel;                      // The data model representing shapes
        private PaintPanel paintPanel;                      // The canvas panel where drawing happens
        private ShapeChooserPanel shapeChooserPanel;        // UI panel for choosing shapes
        private StyleChooser styleChooser;                  // UI panel for choosing styles (fill/outline)

        private Paint paintApp;                             // Reference to the main application
        private Stage stage;                                // The main window

        /**
         * Constructs the View and initializes the GUI.
         */
        public View(PaintModel model, Stage stage, Paint paintApp) {
                this.paintModel = model;
                this.stage = stage;
                this.paintApp = paintApp;

                // Create UI components
                this.shapeChooserPanel = new ShapeChooserPanel(this);
                this.styleChooser = this.shapeChooserPanel.getStyleChooser();
                this.paintPanel = new PaintPanel(this.paintModel, this.shapeChooserPanel, this);

                // Layout using BorderPane
                BorderPane root = new BorderPane();
                root.setTop(createMenuBar());                      // Menu bar at the top
                root.setCenter(this.paintPanel);                   // Canvas in the center
                root.setLeft(this.shapeChooserPanel);              // Shape chooser on the left
                root.setBottom(this.styleChooser);                 // Style chooser at the bottom

                // Scene setup
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Paint");
                stage.show();                                      // Display the window
        }

        // Getters for MVC access
        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        public PaintPanel getPaintPanel() {
                return this.paintPanel;
        }

        public ShapeChooserPanel getShapeChooserPanel() {
                return this.shapeChooserPanel;
        }

        /**
         * Creates the application's menu bar with File and Edit menus.
         */
        private MenuBar createMenuBar() {
                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;

                // File menu
                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Save");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Edit menu
                menu = new Menu("Edit");

                menuItem = new MenuItem("Cut");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Copy");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Paste");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                return menuBar;
        }

        /**
         * Handles menu item actions such as New, Open, Save, Undo, Redo, and Exit.
         */
        @Override
        public void handle(ActionEvent event) {
                CommandInvoker commandInvoker = new CommandInvoker();  // Invoker for executing commands

                // Determine which menu item triggered the event
                String command = ((MenuItem) event.getSource()).getText();
                System.out.println(command); // Debug print

                // Handle each command
                if (command.equals("Exit")) {
                        Platform.exit(); // Closes the application
                }

                if (command.equals("New")) {
                        NewCommand newApp = new NewCommand(stage, paintApp);
                        commandInvoker.executeCommand(newApp);
                }

                if (command.equals("Save")) {
                        SaveCommand save = new SaveCommand(this, paintModel, stage);
                        commandInvoker.executeCommand(save);
                }

                if (command.equals("Open")) {
                        OpenCommand open = new OpenCommand(stage, paintModel, paintPanel);
                        commandInvoker.executeCommand(open);
                }

                if (command.equals("Undo") && !paintModel.getShapes().isEmpty()) {
                        UndoCommand undo = new UndoCommand(paintModel);
                        commandInvoker.executeCommand(undo);
                }

                if (command.equals("Redo") && !paintModel.getUndo().isEmpty()) {
                        RedoCommand redo = new RedoCommand(paintModel);
                        commandInvoker.executeCommand(redo);
                }

                // Refresh the canvas to reflect any changes
                paintPanel.update1();
        }
}
