package agh.ics.oop.model;

import agh.ics.oop.exceptions.IncorrectPositionException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WildOwlBearMap extends AbstractWorldMap {

    private final Vector2d owlBearAreaLowerLeft;
    private final Vector2d owlBearAreaUpperRight;
    protected WildOwlBear wildOwlBear;

    public WildOwlBearMap(int height, int width, int initialPlantCount, int dailyPlantGrowth, int consumeEnergy, WildOwlBear wildOwlBear) {
        super(height, width, initialPlantCount, dailyPlantGrowth, consumeEnergy);
        this.wildOwlBear = wildOwlBear;

        List<Vector2d> owlBearAreaCords = getOwlBearAreaCords(this.lowerLeft, this.upperRight);
        this.owlBearAreaLowerLeft = owlBearAreaCords.get(0);
        this.owlBearAreaUpperRight = owlBearAreaCords.get(1);
    }


    public List<Vector2d> getOwlBearAreaCords(Vector2d lowerLeft, Vector2d upperRight) {
        //WYBÓR kwadratowej podpamy ... TO DO
        return null;
    }


    public void placeWildOwlBear() throws IncorrectPositionException {
        this.place(wildOwlBear);
    }


    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        super.move(animal, direction);
    }


    @Override
    public List<WorldElement> getAllWorldElements() {
        return Stream.concat(Stream.of(wildOwlBear), super.getAllWorldElements().stream())
                .collect(Collectors.toList());
    }


    public boolean isOwlBearMovingBeyondBordersHorizontally(Vector2d position) {
        return (position.getX() > owlBearAreaUpperRight.getX() || position.getX() < owlBearAreaLowerLeft.getX());
    }


    public boolean isOwlBearMovingBeyondBordersVertically(Vector2d position) {
        return (position.getY() > owlBearAreaUpperRight.getY() || position.getY() < owlBearAreaLowerLeft.getY());
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(owlBearAreaLowerLeft) && position.precedes(owlBearAreaUpperRight);
    }

    //List<WorldElement> worldElement = new ArrayList<>(getAllGrassTufts());

    public Vector2d getOwlBearAreaUpperRight() {
        return this.owlBearAreaUpperRight;
    }

    public Vector2d getOwlBearAreaLowerLeft() {
        return this.owlBearAreaLowerLeft;
    }


    public void updateEaten(){
        Vector2d position = this.wildOwlBear.getPosition();
        if (this.animals.containsKey(position)) {
            for ( Animal animal : this.animals.get(position)){
                animal.setPassedAway(true);
                wildOwlBear.incrementAnimalsEaten();
            }
        }
        super.updateEaten();
    }
}
