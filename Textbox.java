package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

import java.util.Objects;
/**
 * Textbox Shape which allows users to add a textbox to the Canvas
 */
public class Textbox extends Shape {
    // Text content of the textbox
    private String text;

    // JavaFX TextArea used for text input (transient so it's not serialized)
    private transient TextArea textArea;

    // Constructor initializes with placeholder text
    public Textbox() {
        this.text = "enter text";
    }

    /**
     * Textbox does not resize, so this method is intentionally left empty.
     */
    @Override
    public void updateSize(double x, double y) {
    }

    /**
     * Draws the text on the canvas using appropriate color and font weight.
     * If it's the default text, use gray and normal weight.
     */
    @Override
    public void draw(GraphicsContext g2d, String mode) {
        if (Objects.equals(this.text, "enter text")) {
            g2d.setFill(Color.DIMGRAY);
            g2d.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        } else {
            g2d.setFill(this.color.toColor());
            g2d.setFont(Font.font("Arial", getFontWeight(), 12));
        }
        g2d.fillText(this.text, this.x, this.y);
    }

    // Sets the text content of the textbox
    private void setText(String text) {
        this.text = text;
    }

    // Checks if the textbox is empty
    private boolean isEmpty() {
        return this.text.isEmpty();
    }

    // Checks if the textbox still contains the default placeholder text
    private boolean isDefault() {
        return this.text.equals("enter text");
    }

    /**
     * Maps outline thickness values to font weights.
     * Acts as a way to simulate different font styles based on the outline setting.
     */
    private FontWeight getFontWeight() {
        int type = this.getOutline();
        FontWeight fontWeight = FontWeight.NORMAL;
        switch (type) {
            case 1: fontWeight = FontWeight.EXTRA_LIGHT; break;
            case 2: fontWeight = FontWeight.LIGHT; break;
            case 3: fontWeight = FontWeight.THIN; break;
            case 4: fontWeight = FontWeight.NORMAL; break;
            case 5: fontWeight = FontWeight.MEDIUM; break;
            case 6: fontWeight = FontWeight.SEMI_BOLD; break;
            case 7: fontWeight = FontWeight.BOLD; break;
            case 8: fontWeight = FontWeight.EXTRA_BOLD; break;
            case 9: fontWeight = FontWeight.BLACK; break;
        }
        return fontWeight;
    }

    /**
     * Displays a transparent TextArea for user input, binds the text to the shape,
     * and removes the TextArea after input is finalized or canceled.
     */
    public void addText(View view, PaintModel model) {
        System.out.println("Textbox started - Press ENTER to add your Text");

        // Set up a hidden TextArea for user input
        textArea = new TextArea();
        textArea.setLayoutX(this.x);
        textArea.setLayoutY(this.y);
        textArea.setMaxSize(100, 10);
        textArea.setOpacity(0); // Invisible, acts as a hidden input layer
        textArea.setFont(Font.font("Arial", getFontWeight(), 12));
        view.getPaintPanel().getChildren().add(textArea);
        textArea.requestFocus(); // Automatically focuses for user input

        // Bind the text input to the Textbox shape's text
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            this.setText(newValue);
            view.getPaintPanel().update(null, null); // Trigger redraw
        });

        // Handle focus loss (clicking away from the textbox)
        textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && this.isDefault()) {
                // If no custom text was entered, remove the shape
                view.getPaintPanel().getChildren().remove(textArea);
                model.removeShape();
            } else if (!newValue) {
                // If text was entered but user clicked away, just remove the TextArea
                view.getPaintPanel().getChildren().remove(textArea);
            }
        });

        // Handle Enter key press to finalize input
        textArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!this.isEmpty()) {
                    this.setText(textArea.getText());
                    view.getPaintPanel().getChildren().remove(textArea);
                    System.out.println("Textbox added");
                    view.getPaintPanel().update(null, null);
                }
            }
        });
    }
}
