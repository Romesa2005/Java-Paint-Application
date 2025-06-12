package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * StyleChooser allows the user to choose between different shape styles (outlined or filled),
 * select a color, and adjust the outline thickness. It is displayed as a row of controls on the GUI.
 */
public class StyleChooser extends GridPane implements EventHandler<ActionEvent> {

    // Reference to the main View
    private View view;

    // ComboBox for selecting style (outlined or filled)
    private ComboBox styleSelector;

    // Currently selected style as a string
    private String styles;

    // Panel for selecting color
    private ColorChooserPanel colorChooserPanel;

    // Panel for selecting outline thickness
    private OutlineThicknessPanel outlineThicknessPanel;

    /**
     * Constructs the StyleChooser and sets up the UI components.
     */
    public StyleChooser(View view) {
        this.view = view;
        int col = 0; // Column index for placing UI components in GridPane

        // Create and configure style selection ComboBox
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("outlined", "filled");
        comboBox.setValue("Select Style");
        this.add(comboBox, 0, 0);
        comboBox.setOnAction(this);
        styleSelector = comboBox;
        col++;

        // Add outline thickness panel (initially disabled)
        outlineThicknessPanel = new OutlineThicknessPanel(view);
        this.add(outlineThicknessPanel, col, 0);
        col++;
        outlineThicknessPanel.setDisable(true);

        // Add color chooser panel
        colorChooserPanel = new ColorChooserPanel(this.view);
        this.add(colorChooserPanel, col, 0);
    }

    // Getter for the style selector ComboBox
    public ComboBox getStyleSelector() {
        return styleSelector;
    }

    // Getter for the outline thickness panel
    public OutlineThicknessPanel getOutlineThicknessPanel() {
        return outlineThicknessPanel;
    }

    // Getter for the currently selected style
    public String getStyles() {
        return styles;
    }

    /**
     * Handles user actions, specifically style selection from the ComboBox.
     * Updates the PaintPanel mode and enables/disables outline thickness panel accordingly.
     */
    public void handle(ActionEvent event) {
        ComboBox<String> command = (ComboBox<String>) event.getSource();
        String style = command.getValue();

        if (style == "outlined") {
            // Set paint mode to "outline" and enable thickness panel
            this.view.getPaintPanel().setMode("outline");
            outlineThicknessPanel.setDisable(false);
            this.styles = style;
        } else {
            // Set paint mode to "fill" and disable thickness panel
            this.view.getPaintPanel().setMode("fill");
            outlineThicknessPanel.setDisable(true);
            this.styles = style;
        }
    }
}
