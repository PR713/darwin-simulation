package agh.ics.oop.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {

    private final int maxWidth;
    private final int maxHeight;
    private final int amountOfGrass;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int amountOfGrass) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.amountOfGrass = amountOfGrass;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new RandomPositionIterator(maxWidth, maxHeight, amountOfGrass);
    }
}
