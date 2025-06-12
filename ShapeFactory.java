package ca.utoronto.utm.assignment2.paint;
public class ShapeFactory {
    // Default constructor - no special initialization needed
    public ShapeFactory() {
        // NOT STUB
    }

    /**
     * Factory method to create a new Shape object based on the shapeType string.
     * Returns a new instance of the corresponding Shape subclass.
     * If shapeType is null or does not match any known shape, returns null.
     *
     * @param shapeType the name of the shape to create
     * @return a new Shape instance or null if no match
     */
    public Shape getNewShape(String shapeType) {
        if (shapeType == null) {
            return null; // No shape type provided, return null
        } else if (shapeType.equals("Rectangle")) {
            return new Rectangle(); // Return new Rectangle instance
        } else if (shapeType.equals("Square")) {
            return new Square(); // Return new Square instance
        } else if (shapeType.equals("Oval")) {
            return new Oval(); // Return new Oval instance
        } else if (shapeType.equals("Circle")) {
            return new Circle(); // Return new Circle instance
        } else if (shapeType.equals("Polyline")) {
            return new Polyline(); // Return new Polyline instance
        } else if (shapeType.equals("Squiggle")) {
            return new Squiggle(); // Return new Squiggle instance
        } else if (shapeType.equals("Triangle")) {
            return new Triangle(); // Return new Triangle instance
        } else if (shapeType.equals("Textbox")) {
            return new Textbox(); // Return new Textbox instance
        }
        return null; // No matching shape found, return null
    }
}
