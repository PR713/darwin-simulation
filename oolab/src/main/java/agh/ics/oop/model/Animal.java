package agh.ics.oop.model;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;

    public Animal() {
        this.position = new Vector2d(2,2);
    }

    public Animal(Vector2d vector) {
        this.position = vector;
    }

    @Override
    public String toString(){
        return "(%d, %d) , %s".formatted(this.position.getX(), this.position.getY(), orientation);
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public MapDirection getOrientation(){
        return this.orientation;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void move(MoveDirection direction) {
        Vector2d newPosition = this.position;
        switch(direction) {
            case LEFT: this.orientation = orientation.previous();
                break;
            case RIGHT: this.orientation = orientation.next();
                break;
            case FORWARD:
                switch (this.orientation) {
                    case NORTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()+1);
                        break;
                    case SOUTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()-1);
                        break;
                    case EAST: newPosition = new Vector2d(this.position.getX()+1, this.position.getY());
                        break;
                    case WEST: newPosition = new Vector2d(this.position.getX()-1, this.position.getY());
                        break;
                }; break;
            case BACKWARD:
                switch (this.orientation) {
                    case NORTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()-1);
                        break;
                    case SOUTH: newPosition = new Vector2d(this.position.getX(), this.position.getY()+1);
                        break;
                    case EAST: newPosition = new Vector2d(this.position.getX()-1, this.position.getY());
                        break;
                    case WEST: newPosition = new Vector2d(this.position.getX()+1, this.position.getY());
                        break;
                }; break;
        };
        if (newPosition.getX() >= 0 && newPosition.getX() <= 4 &&
                newPosition.getY() >= 0 && newPosition.getY() <= 4) {
            this.position = newPosition;
        }
    }
}
