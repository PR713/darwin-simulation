package agh.ics.oop.model;

import agh.ics.oop.model.RectangularMap;
import java.sql.Array;
import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> grassTufts = new HashMap<>();
    private final int numOfGrassFields;
    private final int maxDimension;

    public GrassField(int numOfGrassFields){
        super(new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE), new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.numOfGrassFields = numOfGrassFields;
        this.maxDimension = (int) Math.ceil(Math.sqrt(numOfGrassFields*10));
        generateGrassPositions();
    }

    private void generateGrassPositions(){
        List<Vector2d> allPositions = new ArrayList<>();
        for (int x = 0; x <= maxDimension; x++){
            for (int y = 0; y <= maxDimension; y++) {
                allPositions.add(new Vector2d(x,y));
            }
        }

        Collections.shuffle(allPositions);
        for (int i = 0; i < numOfGrassFields; i++) {
            Vector2d position = allPositions.get(i);
            grassTufts.put(position, new Grass(position));
        }
    }


    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition()) ) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return grassTufts.containsKey(position);
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animalExist = super.objectAt(position);
        if (animalExist == null){
            if (isOccupied(position)){
                return grassTufts.get(position);
            } else return null;
        } else return animalExist;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return !super.isOccupied(position);
    }


    public String toString(){

        return "";
    }
}
