package agh.ics.oop.model;

public class Grass implements WorldElement {
    private final Vector2d tuftPosition;
    private final float energy;

    public Grass(Vector2d tuftPosition, float energy){
        this.energy = energy;
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
}
