package agh.ics.oop.model;

import java.util.*;

import agh.ics.oop.model.GrassField;

public class RandomPositionGenerator implements Iterable<Vector2d> {

    private final List<Vector2d> availablePositions;
    private final Iterator<Vector2d> iterator;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int amountOfGrass) {
        this.availablePositions = new ArrayList<>();
        for (int x = 0; x <= maxWidth; x++){
            for (int y = 0; y <= maxHeight; y++) {
                availablePositions.add(new Vector2d(x,y));
            }
        }

        Collections.shuffle(availablePositions);
        List<Vector2d> selectedPositions = new ArrayList<>();

        for (int i = 0; i < amountOfGrass; i++) {
            Vector2d position = availablePositions.get(i);
            selectedPositions.add(position);
        }

        iterator = selectedPositions.iterator();
    }


    @Override
    public Iterator<Vector2d> iterator() {
        return iterator;
    }
}
