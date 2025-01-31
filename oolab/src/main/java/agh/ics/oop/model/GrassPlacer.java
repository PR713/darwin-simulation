package agh.ics.oop.model;

import java.util.*;

public class GrassPlacer {

    private final Map<Vector2d, Grass> grassTufts;
    private final Vector2d lowerLeftEquatorialForest;
    private final Vector2d upperRightEquatorialForest;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    final int consumeEnergy; // nazwa + modyfikator dostępu?
    private int dailyGrassGrowth;

    public GrassPlacer(Map<Vector2d, Grass> grassTufts, Vector2d lowerLeft, Vector2d upperRight,
                       Vector2d lowerLeftEquatorialForest, Vector2d upperRightEquatorialForest, int consumeEnergy,
                       int dailyGrassGrowth, int initialPlantCount) {
        this.grassTufts = grassTufts;
        this.lowerLeftEquatorialForest = lowerLeftEquatorialForest;
        this.upperRightEquatorialForest = upperRightEquatorialForest;
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.consumeEnergy = consumeEnergy;
        this.dailyGrassGrowth = initialPlantCount;
        addGrassTufts();
        this.dailyGrassGrowth = dailyGrassGrowth;
    }


    public Set<Vector2d> findEmptySpots(Vector2d leftCorner, Vector2d rightCorner) {
        Set<Vector2d> potentialPositions = new HashSet<>();

        for (int x = leftCorner.getX(); x <= rightCorner.getX(); x++) {
            for (int y = leftCorner.getY(); y <= rightCorner.getY(); y++) {
                Vector2d position = new Vector2d(x, y);
                if (!grassTufts.containsKey(position)) {
                    potentialPositions.add(position);
                }
            }
        }

        return potentialPositions;
    }


    public void addGrassTufts() {
        Set<Vector2d> potentialPositionsInEquatorialForest = findEmptySpots(lowerLeftEquatorialForest, upperRightEquatorialForest);
        Set<Vector2d> potentialPositionsOutsideEquatorialForest = new HashSet<>();

        Vector2d upperRightPotentialPositions1 = new Vector2d(upperRightEquatorialForest.getX(), lowerLeftEquatorialForest.getY() - 1);
        if (upperRightPotentialPositions1.precedes(upperRight)) {
            potentialPositionsOutsideEquatorialForest.addAll(findEmptySpots(lowerLeft, upperRightPotentialPositions1));
        }

        Vector2d lowerLeftPotentialPositions2 = new Vector2d(lowerLeftEquatorialForest.getX(), upperRightEquatorialForest.getY() + 1);
        if (lowerLeftPotentialPositions2.follows(lowerLeft)) {
            potentialPositionsOutsideEquatorialForest.addAll(findEmptySpots(lowerLeftPotentialPositions2, upperRight));
        }

        for (int i = 0; i < dailyGrassGrowth; i++) {
            Set<Vector2d> potentialPositions;
            if (Math.random() < 0.8 && !potentialPositionsInEquatorialForest.isEmpty()) {
                potentialPositions = potentialPositionsInEquatorialForest;
            } else if (!potentialPositionsOutsideEquatorialForest.isEmpty()) {
                potentialPositions = potentialPositionsOutsideEquatorialForest;
            } else {
                break; // Oba zbiory są puste, nie ma gdzie dodać trawy
            }

            Vector2d[] positionsArray = potentialPositions.toArray(new Vector2d[0]);
            Vector2d chosenPosition = positionsArray[(int) (Math.random() * positionsArray.length)];

            grassTufts.put(chosenPosition, new Grass(chosenPosition));

            potentialPositions.remove(chosenPosition);
        }
    }
}
