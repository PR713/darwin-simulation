package agh.ics.oop.model;

import static agh.ics.oop.model.MapDirection.fromNumericValue;

public class WildOwlBear extends AbstractAnimal {


    public WildOwlBear(Vector2d vector, MapDirection orientation) {
        super(vector, orientation);
    }


    // może wywołuje super ale z opcjonalnym argumentem przekazywanym
    //do canMoveTo który określa granicę?

    @Override
    public void move(MoveValidator validator, int direction) {
        MapDirection newOrientation = fromNumericValue((this.orientation.getNumericValue() + direction) % 8);
        Vector2d newPosition = this.position.add(this.orientation.toMapDirectionVector());

        if (validator.canMoveTo(newPosition)) {
            this.position = newPosition;
            this.orientation = newOrientation;
        } else {
            if (validator.isMovingBeyondBordersHorizontally(newPosition)
                    && validator.isMovingBeyondBordersVertically(newPosition)) {
                //position doesn't change
                this.orientation = newOrientation.reverseOrientation();

            } else if (validator.isMovingBeyondBordersHorizontally(newPosition)) {
                this.position = this.position.getX() > validator.getUpperRight().getX() ?
                        new Vector2d(0, this.position.getY()) :
                        new Vector2d(validator.getUpperRight().getX(), this.position.getY());
                this.orientation = newOrientation.reverseOrientation();
            } else { // vertically but not in corners, position doesn't change
                this.orientation = newOrientation.reverseOrientation();
            }

        }
    }
}
