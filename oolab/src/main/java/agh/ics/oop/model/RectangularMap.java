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
        animals.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(this, direction);
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public Animal objectAt(Vector2d position) {
        if (isOccupied(position)) {return animals.get(position);}
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.precedes(upperRight) && position.follows(lowerLeft) && (!isOccupied(position))) {
            return true;}
        return false;
    }

    @Override
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowerLeft,upperRight);
    }
}
