package ca.utoronto.utm.assignment2.paint;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * OutlineThicknessPanel provides a dropdown for the user to select the outline thickness.
 * It communicates the selected value to the PaintPanel through the View.
 */

public class OutlineThicknessPanel extends GridPane implements EventHandler<ActionEvent> {

    // Default outline width
    private int width = 5;

    // Reference to the main View object to access the PaintPanel
    private View view;

    /**
     * Constructor for OutlineThicknessPanel.
     * Initializes a ComboBox with outline width options and adds it to the panel.
     *
     * @param view The main view of the application.
     */
    public OutlineThicknessPanel(View view) {
        this.view = view;

        // Create a ComboBox with outline thickness options from 1 to 9
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9");

        // Attempt to set default display value (will not match items — see note below)
        comboBox.setValue("Default Width: 1");

        // Add ComboBox to the GridPane layout at column 0, row 0
        this.add(comboBox, 0, 0);

        // Set this class as the handler for ComboBox selection events
        comboBox.setOnAction(this);
    }

    /**
     * Handles ComboBox selection changes.
     * Updates the outline width in the PaintPanel based on user selection.
     *
     * @param event The ActionEvent triggered by the ComboBox.
     */
    @Override
    public void handle(ActionEvent event) {
        // Retrieve the selected item from the ComboBox
        ComboBox<String> comboBox = (ComboBox<String>) event.getSource();
        String width = comboBox.getValue();

        // Update the outline width in the PaintPanel
        this.view.getPaintPanel().setOutline(Integer.parseInt(width));

        // Store the new width in the internal field
        this.width = Integer.parseInt(width);
    }
}
