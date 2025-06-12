package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.io.Serializable;
/**
 * A SerializableColor stores the data of a colour, allowing it to be saved.
 */
public class SerializableColor implements Serializable {
    // RGBA components of the color
    public double red;
    public double green;
    public double blue;
    public double alpha;

    // Constructor that takes a JavaFX Color and extracts its RGBA values
    public SerializableColor(Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getOpacity();
    }

    // Constructor that initializes color directly with RGBA values
    public SerializableColor(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    // Converts this SerializableColor back to a JavaFX Color object
    public Color toColor() {
        return new Color(red, green, blue, alpha);
    }
}
