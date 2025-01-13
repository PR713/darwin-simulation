package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GrassPlacer {

    private final Map<Vector2d, WorldElement> grassTufts;
    private final Vector2d lowerLeftEquatorialForest;
    private final Vector2d upperRightEquatorialForest;
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    final int consumeEnergy;

    public GrassPlacer(Map<Vector2d, WorldElement> grassTufts, Vector2d lowerLeft, Vector2d upperRight,
                Vector2d lowerLeftEquatorialForest, Vector2d upperRightEquatorialForest, int consumeEnergy) {
        this.grassTufts = grassTufts;
        this.lowerLeftEquatorialForest = lowerLeftEquatorialForest;
        this.upperRightEquatorialForest = upperRightEquatorialForest;
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.consumeEnergy = consumeEnergy;
    }

    public List<Vector2d> findEmptySpots(Vector2d leftCorner, Vector2d rightCorner) {
        List<Vector2d> potentialPositions = new ArrayList<>();

        // Iteracja po obszarze i dodanie pustych pozycji do listy
        for (int x = lowerLeft.getX(); x <= upperRight.getX(); x++) {
            for (int y = lowerLeftEquatorialForest.getY(); y <= upperRightEquatorialForest.getY(); y++) {
                Vector2d position = new Vector2d(x, y);
                if (!grassTufts.containsKey(position)) {
                    potentialPositions.add(position);
                }
            }
        }

        return potentialPositions;
    }


    public void addGrassTuft() {
        List<Vector2d> potentialPositions;
        if (Math.random() < 0.8) {
            potentialPositions = findEmptySpots(lowerLeftEquatorialForest, upperRightEquatorialForest);
        } else {
            potentialPositions = new ArrayList<>();
            Vector2d upperRightPotentialPositions1 = new Vector2d(upperRightEquatorialForest.getX() - 1, lowerLeftEquatorialForest.getY() - 1);
            if (upperRightPotentialPositions1.precedes(upperRight)) {
                List<Vector2d> potentialPositions1 = findEmptySpots(lowerLeft, upperRightPotentialPositions1);
                potentialPositions.addAll(potentialPositions1);
            }

            Vector2d lowerLeftPotentialPositions2 = new Vector2d(lowerLeftEquatorialForest.getX() + 1, upperRightEquatorialForest.getY() + 1);
            if (lowerLeftPotentialPositions2.follows(lowerLeft)) {
                List<Vector2d> potentialPositions2 = findEmptySpots(lowerLeftPotentialPositions2, upperRight);
                potentialPositions.addAll(potentialPositions2);
            }
        }

        if (!potentialPositions.isEmpty()) {
            Vector2d potentialPosition = potentialPositions.get((int) (Math.random() * potentialPositions.size()));
            grassTufts.put(potentialPosition, new Grass(potentialPosition));
        }
    }
}
