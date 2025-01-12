package agh.ics.oop.model;

public class Grass implements WorldElement {
    private final Vector2d tuftPosition;
    private final int consumeEnergy;

    public Grass(Vector2d tuftPosition, int consumeEnergy){
        this.consumeEnergy = consumeEnergy;
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
