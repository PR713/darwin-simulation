package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RectangularMap implements WorldMap {
    private final Vector2d size;
    //private final MapVisualizer visualizer;
    private final Vector2d upperRight;
    private final Vector2d lowerLeft;

    Map<Vector2d, Animal> animals = new HashMap<>();

    public RectangularMap(int width, int height){
        size = new Vector2d(width, height);
        upperRight = new Vector2d(width - 1, height - 1);
        lowerLeft = new Vector2d(0,0);
    }

    @Override
    public boolean place(Animal animal) {
        if (animals.containsKey(animal.getPosition()))
           return false;
        return true;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Animal objectAt(Vector2d position) {
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if ((position.follows(upperRight) || position.precedes(lowerLeft)))
            return false;
        return true;
    }
}
