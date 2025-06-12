package ca.utoronto.utm.assignment2.paint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 * PaintModel acts as the data model in the MVC architecture.
 * It maintains a list of drawn shapes and supports undo/redo functionality.
 *
 * It extends Observable, so views can observe changes to this model.
 */
public class PaintModel extends Observable implements Serializable {

        // List of shapes currently on the canvas
        private ArrayList<Shape> shapes = new ArrayList<Shape>();

        // List of shapes that were undone and can be redone
        private ArrayList<Shape> undoshapes = new ArrayList<Shape>();

        /**
         * Adds a new shape to the canvas.
         * Clears the undo list since a new action was taken.
         * Notifies observers of the change.
         *
         * @param r The shape to add
         */
        public void addShape(Shape r){
                undoshapes.clear();
                this.shapes.add(r);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Removes the most recently added shape and stores it in the undo list.
         * Notifies observers of the change.
         */
        public void undo() {
                if (!shapes.isEmpty()) {
                        undoshapes.add(shapes.removeLast());
                        this.setChanged();
                        this.notifyObservers();
                }
        }

        /**
         * Restores the most recently undone shape.
         * Does not notify observers, which may be a design oversight.
         */
        public void redo() {
                if (!undoshapes.isEmpty()) {
                        shapes.add(undoshapes.removeLast());
                }
        }

        /**
         * Removes and returns the most recent shape from the undo list.
         * Notifies observers of the change.
         *
         * @return The removed shape
         */
        public Shape removeUndo(){
                Shape x = this.undoshapes.get(this.undoshapes.size() - 1);
                undoshapes.remove(this.undoshapes.size() - 1);
                this.setChanged();
                this.notifyObservers();
                return x;
        }

        /**
         * Returns the current list of shapes on the canvas.
         *
         * @return The shapes list
         */
        public ArrayList<Shape> getShapes(){
                return shapes;
        }

        /**
         * Returns the list of shapes that were undone.
         *
         * @return The undo shapes list
         */
        public ArrayList<Shape> getUndo(){
                return undoshapes;
        }

        /**
         * Adds a shape to the canvas without clearing the undo list.
         * Notifies observers of the change.
         *
         * @param r The shape to add
         */
        public void addShapeRedo(Shape r){
                this.shapes.add(r);
                this.setChanged();
                this.notifyObservers();
        }

        /**
         * Removes and returns the most recent shape from the canvas.
         * Notifies observers of the change.
         *
         * @return The removed shape
         */
        public Shape removeShape(){
                Shape x = this.shapes.get(this.shapes.size() - 1);
                shapes.remove(this.shapes.size() - 1);
                this.setChanged();
                this.notifyObservers();
                return x;
        }
}
