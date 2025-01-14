package agh.ics.oop.model;

public class Grass implements WorldElement {
    private final Vector2d tuftPosition;


    public Grass(Vector2d tuftPosition){
        this.tuftPosition = tuftPosition;
    }


    @Override
    public Vector2d getPosition(){
        return this.tuftPosition;
    }


    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String getResourceName() {
        return "grass.png";
    }
}
