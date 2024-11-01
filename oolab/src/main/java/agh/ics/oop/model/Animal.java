package agh.ics.oop.model;
import agh.ics.oop.model.Vector2d;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    public Vector2d position;

    public Animal() {
        this.position = new Vector2d(2,2);
    }

    public Animal(Vector2d vector) {
        this.position = vector;
    }

    public String toString(){
        return "Pozycja zwierzaka (%s, %s), orientacja: %s".formatted(this.position.getX(), this.position.getY(), this.orientation);
    }

    public boolean isAt(Vector2d position) {
        return this.position == position;
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition = this.position;
        switch(direction) {
            case LEFT: this.orientation = orientation.previous();
            case RIGHT: this.orientation = orientation.next();
            case FORWARD:
                switch (this.orientation) {
                    case NORTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()+1);
                    case SOUTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()-1);
                    case EAST: newPosition = new Vector2d(this.position.getX()+1, this.position.getY());
                    case WEST: newPosition = new Vector2d(this.position.getX()-1, this.position.getY());
                };
            case BACKWARD:
                switch (this.orientation) {
                    case NORTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()-1);
                    case SOUTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()+1);
                    case EAST: newPosition = new Vector2d(this.position.getX()-1, this.position.getY());
                    case WEST: newPosition = new Vector2d(this.position.getX()+1, this.position.getY());
                };
        };
        if (position.getX() >= 0 && position.getX() <= 4 &&
                position.getY() >= 0 && position.getY() <= 4) {
            this.position = newPosition;
        }
    }


}
