package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected final MapVisualizer visualizer;

    public AbstractWorldMap(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.visualizer = new MapVisualizer(this);
    }


    @Override
    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
        } else {
            throw new IncorrectPositionException(animal.getPosition());
        }

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
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }


    @Override
    public List<WorldElement> getElements(){
        return List.copyOf(animals.values());
    }

}
