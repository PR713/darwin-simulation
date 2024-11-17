package agh.ics.oop.model;

import java.util.*;

public class RandomPositionIterator implements Iterator<Vector2d> {
    private final int maxWidth;
    private final int maxHeight;
    private final List<Integer> allIndicesList;
    private int remainingToGenerate;
    private Random random;
    private int rangeOfChoosing;

    public RandomPositionIterator(int maxWidth, int maxHeight, int amountOfGrass) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.remainingToGenerate = amountOfGrass;
        this.allIndicesList = new ArrayList<>();
        this.rangeOfChoosing = (maxHeight + 1) * (maxWidth + 1) - 1;

        for (int i = 0; i < (maxHeight + 1) * (maxWidth + 1); i++) {
            allIndicesList.add(i); // (x,y) = (i/n,i%n)
        }
    }


    @Override
    public boolean hasNext() {
        return remainingToGenerate > 0;
    }

    @Override
    public Vector2d next() {
        int randomIndex = random.nextInt(rangeOfChoosing);
        int actualIndex = allIndicesList.get(randomIndex);

        Collections.swap(allIndicesList, randomIndex, allIndicesList.size() - 1);
        remainingToGenerate -= 1;
        rangeOfChoosing -= 1;
        return new Vector2d(actualIndex % maxWidth, actualIndex / maxWidth);
    }
}
