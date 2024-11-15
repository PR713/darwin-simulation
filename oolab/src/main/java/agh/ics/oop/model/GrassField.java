package agh.ics.oop.model;

public class GrassField implements WorldMap {
    private int numOfGrassFields;

    public GrassField(int numOfGrassFields){
        this.numOfGrassFields = numOfGrassFields;

    }


    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }
}
