package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        // Reference to the main View object to interact with the overall UI and model
        private View view;
        // Label to display the current selected shape mode
        private Label modelabel;
        // Keeps track of the currently active (selected) shape button
        private Button activeButton;
        // Stores the id/name of the selected shape
        private String selectedShape;
        // Reference to the style chooser panel to control style options based on shape
        private StyleChooser styleChooser;

        public ShapeChooserPanel(View view) {

                // Initialize the style chooser with the view reference
                this.styleChooser= new StyleChooser(view);
                this.view = view;

                // List of shape button labels to create buttons for
                String[] buttonLabels = {"Rectangle", "Square", "Oval", "Circle", "Triangle", "Squiggle", "Polyline", "Textbox" };

                int row = 0;
                Image icon;
                ImageView iconView;
                // Loop through each shape name to create a button with an icon
                for (String label : buttonLabels) {
                        // Load image icon from resources
                        icon = new Image(getClass().getResourceAsStream("/images/" + label + ".png"));
                        iconView = new ImageView(icon);
                        // Set icon size for the button
                        iconView.setFitWidth(20);
                        iconView.setFitHeight(20);
                        // Create a new button with the icon and no text
                        Button button = new Button("", iconView);
                        // Assign the shape name as the button's ID for identification
                        button.setId(label);
                        // Set minimum button width for consistent UI
                        button.setMinWidth(100);
                        // Add the button to the GridPane at column 0 and current row
                        this.add(button, 0, row);
                        row++;
                        // Set this class as the event handler for button clicks
                        button.setOnAction(this);
                }

                // Create a label to display the current selected mode
                modelabel = new Label("Current Mode: ");
                modelabel.setMaxSize(100, 100);
                // Add the label below all the buttons in the grid
                this.add(modelabel, 0, row);

        }

        @Override
        public void handle(ActionEvent event) {
                // If there is a shape currently being drawn on the paint panel,
                // add it to the paint model and reset the shape in the paint panel
                if (this.view.getPaintPanel().shape != null)
                {
                        this.view.getPaintModel().addShape(this.view.getPaintPanel().shape);
                        this.view.getPaintPanel().shape = null;
                }

                // Get the button that was clicked
                Button clickedButton = (Button) event.getSource();
                // Get the ID (shape name) of the clicked button
                String command = ((Button) event.getSource()).getId();
                // Update selectedShape to the clicked button's ID
                selectedShape = command;

                // If there was a previously active button, reset its style to default
                if (activeButton != null) {
                        activeButton.setStyle("");
                }

                // Enable/disable style chooser controls depending on selected shape and style
                if (selectedShape == "Polyline"){
                        // For Polyline, enable both style selector and outline thickness
                        styleChooser.getStyleSelector().setDisable(false);
                        styleChooser.getOutlineThicknessPanel().setDisable(false);
                }
                else if (selectedShape == "Squiggle"){
                        // For Squiggle, disable style selector, enable outline thickness
                        styleChooser.getStyleSelector().setDisable(true);
                        styleChooser.getOutlineThicknessPanel().setDisable(false);
                }
                else if (styleChooser.getStyles()!=null && styleChooser.getStyles().equals("filled")){
                        // If style is filled, enable style selector but disable outline thickness
                        styleChooser.getStyleSelector().setDisable(false);
                        styleChooser.getOutlineThicknessPanel().setDisable(true);
                }
                else if (styleChooser.getStyles()!=null &&styleChooser.getStyles().equals("outlined")){
                        // If style is outlined, enable both controls
                        styleChooser.getStyleSelector().setDisable(false);
                        styleChooser.getOutlineThicknessPanel().setDisable(false);
                }
                else{
                        // Default case: disable outline thickness, enable style selector
                        styleChooser.getOutlineThicknessPanel().setDisable(true);
                        styleChooser.getStyleSelector().setDisable(false);
                }

                // Highlight the clicked button with a light blue background to show it is active
                clickedButton.setStyle("-fx-background-color: lightblue");
                // Update activeButton reference
                activeButton = clickedButton;

                // Update the mode label to show the currently selected shape
                modelabel.setText("Current Mode: \n"+ clickedButton.getId());
        }

        // Getter for the style chooser panel
        public StyleChooser getStyleChooser() {
                return styleChooser;
        }

        // Returns a new Shape object based on the currently selected shape name
        public Shape getSelectedShape() {
                ShapeFactory shapeFactory = new ShapeFactory();
                return shapeFactory.getNewShape(selectedShape);
        }
}

