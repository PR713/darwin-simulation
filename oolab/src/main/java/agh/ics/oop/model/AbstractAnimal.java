package agh.ics.oop.model;
//move to samo, canMoveTo mają ograniczone


import static agh.ics.oop.model.MapDirection.fromNumericValue;

public class AbstractAnimal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;

    public AbstractAnimal(Vector2d vector, MapDirection orientation) {
        this.position = vector;
        this.orientation = orientation;
    }


    @Override
    public String toString() {
        return switch (this.getOrientation()) {
            case N -> "^";
            case E -> ">";
            case S -> "v";
            case W -> "<";
            case NE -> "↗";
            case SE -> "↘";
            case SW -> "↙";
            case NW -> "↖";
        };
    }


    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }


    public MapDirection getOrientation() {
        return this.orientation;
    }


    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public void move(MoveValidator validator, int direction) {
        MapDirection newOrientation = fromNumericValue((this.orientation.getNumericValue() + direction) % 8);
        Vector2d newPosition = this.position.add(this.orientation.toMapDirectionVector());

        if (validator.canMoveTo(newPosition)) {
            this.position = newPosition;
            this.orientation = newOrientation;
        }
    }


}

