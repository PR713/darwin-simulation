package agh.ics.oop.model;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grassTufts = new HashMap<>();
    private static final Vector2d MIN_VALUE = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final Vector2d MAX_VALUE = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);


    public GrassField(int numOfGrassFields){
        super(MIN_VALUE, MAX_VALUE);
        int maxDimensionOfGrassFields = (int) Math.floor(Math.sqrt(numOfGrassFields * 10));

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxDimensionOfGrassFields, maxDimensionOfGrassFields, numOfGrassFields);
        Iterator<Vector2d> positionsIterator = randomPositionGenerator.iterator();

        while(positionsIterator.hasNext()) {
            Vector2d grassPosition = positionsIterator.next();
            grassTufts.put(grassPosition, new Grass(grassPosition));
        }
    }


    @Override
    public boolean isOccupied(Vector2d position){
        return super.isOccupied(position) || grassTufts.containsKey(position);
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animalExist = super.objectAt(position);
        return (animalExist != null) ? animalExist : grassTufts.get(position);
        //get(position) jeśli nie ma to da nulla
    }


    @Override
    public Boundary getCurrentBounds(){
        Vector2d maxiValTemp = MIN_VALUE;
        Vector2d miniValTemp = MAX_VALUE;

        for (Vector2d position : animals.keySet()) {
            miniValTemp = miniValTemp.lowerLeft(position);
            maxiValTemp = maxiValTemp.upperRight(position);
        }

        for (Vector2d position : grassTufts.keySet()) {
            miniValTemp = miniValTemp.lowerLeft(position);
            maxiValTemp = maxiValTemp.upperRight(position);
        }

        return new Boundary(miniValTemp, maxiValTemp);
    }


    @Override
    public List<WorldElement> getElements(){
        List<WorldElement> allElements = new ArrayList<>(super.getElements());
        allElements.addAll(grassTufts.values());
        return Collections.unmodifiableList(allElements);
    }


    Map<Vector2d, Grass> getGrassTufts() {
        return Collections.unmodifiableMap(grassTufts);
    }
}

