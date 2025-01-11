package agh.ics.oop.model;

public interface MoveValidator {

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    boolean isMovingBeyondBordersHorizontally(Vector2d position);

    boolean isMovingBeyondBordersVertically(Vector2d position);

    Vector2d getUpperRight();
}