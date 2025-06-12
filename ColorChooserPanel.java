package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
/**
 * This class creates a color picker panel and handles color selection events.
 */

public class ColorChooserPanel extends GridPane implements EventHandler<ActionEvent> {
    // Default color is set to black
    private Color color = Color.BLACK;
    // Reference to the View component
    private View view;

    /**
     * Constructor for ColorChooserPanel.
     * Sets up a ColorPicker UI component and adds it to the GridPane layout.
     *
     * @param view The main View of the application (used to update the PaintPanel)
     */
    public ColorChooserPanel(View view) {
        this.view = view;

        // Create a ColorPicker and set its initial color to black
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);

        // Add the ColorPicker to the panel at column 1, row 0
        this.add(colorPicker, 1, 0);

        // Register this class as the event handler for the ColorPicker
        colorPicker.setOnAction(this);
    }

    /**
     * Handles color selection events from the ColorPicker.
     * Updates the paint panel with the selected color.
     *
     * @param actionEvent the event triggered when the user picks a color
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // Get the selected color from the ColorPicker
        Color currentColor = ((ColorPicker) actionEvent.getSource()).getValue();

        // Update the paint panel's color in the view
        this.view.getPaintPanel().setColor(currentColor);

        // Update the internal color state
        this.color = currentColor;
    }
}
