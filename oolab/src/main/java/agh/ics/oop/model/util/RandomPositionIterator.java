package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomPositionIterator implements Iterator<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final List<Integer> allIndicesList;
    private int remainingToGenerate;
    private final Random random;
    private int rangeOfChoosing;

    public RandomPositionIterator(int maxWidth, int maxHeight, int countOfAnimals) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.remainingToGenerate = countOfAnimals;
        this.allIndicesList = new ArrayList<>();
        this.rangeOfChoosing = maxHeight * maxWidth - 1;
        this.random = new Random();

        for (int i = 0; i <= rangeOfChoosing; i++) {
            allIndicesList.add(i); // (x,y) = (i%n,i/n)
        }
    }


    @Override
    public boolean hasNext() {
        return remainingToGenerate > 0;
    }

    @Override
    public Vector2d next() {
        System.out.println("Randomizer: " + rangeOfChoosing);
        int randomIndex = random.nextInt(rangeOfChoosing + 1);
        int actualIndex = allIndicesList.get(randomIndex);

        rangeOfChoosing -= 1;
        remainingToGenerate -= 1;
        return new Vector2d(actualIndex % maxWidth, actualIndex / maxWidth);
    }
}