package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, Grass> grassTufts = new HashMap<>();
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final Collection<WorldElement> elements = new ArrayList<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;

    public AbstractWorldMap(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }


    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            return true;
        }
        return false;
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
        if (isOccupied(position)) {return animals.get(position);}
        return null;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight) && position.follows(lowerLeft) && (!isOccupied(position)));
    }


    @Override
    public Collection<WorldElement> getElements(){
        elements.addAll(animals.values());
        elements.addAll(grassTufts.values());

        return elements;
    }
}
