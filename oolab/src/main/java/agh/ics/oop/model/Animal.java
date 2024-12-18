package agh.ics.oop.model;

public class Animal implements WorldElement {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private static final Vector2d upperRight = new Vector2d(4,4);
    private static final Vector2d lowerLeft = new Vector2d(0,0);

    public Animal() {
        this.position = new Vector2d(2,2);
    }

    public Animal(Vector2d vector) {
        this.position = vector;
    }


    @Override
    public String toString(){
        return switch (this.getOrientation()) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }


    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }


    public MapDirection getOrientation(){
        return this.orientation;
    }


    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    public void move(MoveValidator validator, MoveDirection direction) {
        Vector2d newPosition = this.position;
        switch(direction) {
            case LEFT: this.orientation = orientation.previous();
                break;
            case RIGHT: this.orientation = orientation.next();
                break;
            case FORWARD:
                newPosition = this.position.add(this.orientation.toUnitVector());
                break;
            case BACKWARD:
                newPosition = this.position.subtract(this.orientation.toUnitVector());
                break;
        };
        if (validator.canMoveTo(newPosition)){ //validator to mapa
            this.position = newPosition; //wywołane na animal
        }
    }
}
