package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {

    private final int maxWidth;
    private final int maxHeight;
    private final int countOfAnimals;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int countOfAnimals) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.countOfAnimals = countOfAnimals;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator(maxWidth, maxHeight, countOfAnimals);
    }
}