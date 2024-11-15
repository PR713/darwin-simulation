package agh.ics.oop.model;

public class Grass implements WorldElement {
    private final Vector2d tuftPosition;

    public Grass(Vector2d tuftPosition){
        this.tuftPosition = tuftPosition;
    }

    public Vector2d getPosition(){
        return this.tuftPosition;
    }

    public String toString() {
        return "*";
    }
}
