package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class WildOwlBearMap extends AbstractWorldMap {

    private final Vector2d owlBearAreaLowerLeft;
    private final Vector2d owlBearAreaUpperRight;
    protected WildOwlBear wildOwlBear;

    public WildOwlBearMap(int height, int width, int initialPlantCount, int dailyPlantGrowth, int consumeEnergy,
                          Vector2d owlBearAreaLowerLeft, Vector2d owlBearAreaUpperRight, WildOwlBear wildOwlBear) {
        super(height, width, initialPlantCount, dailyPlantGrowth, consumeEnergy);
        this.wildOwlBear = wildOwlBear;
        this.owlBearAreaLowerLeft = owlBearAreaLowerLeft;
        this.owlBearAreaUpperRight = owlBearAreaUpperRight;
    }

    @Override
    public void move(AbstractAnimal animal, MapDirection direction) {
        //lub osobna klasa dla OldAgeAnimal i każda klasa metodę MovementBehavior sama implementuje
        if (animal instanceof Animal) {
                super.move(animal, direction);
        }//else
        Vector2d oldPosition = animal.getPosition();
        animal.move(this, direction.getNumericValue());
        Vector2d newPosition = animal.getPosition();

        if (!oldPosition.equals(newPosition)) {
            setOwlBearPosition(newPosition);
            mapChanged(String.format("OwlBear moved from %s to %s", oldPosition, newPosition));
        }
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

    protected void owlBearAteAnAnimal(Vector2d position) {
        List<? extends WorldElement> animalsAteByOwlBear = this.animals.get(position);
        for (WorldElement animal : animalsAteByOwlBear) {
            ((Animal) animal).setPassedAway(true);
            this.currentAnimalCount--;
            wildOwlBear.incrementAnimalsEaten();
        }
        this.animals.remove(position);
    }

    protected void setOwlBearPosition(Vector2d position) {
        this.wildOwlBear.position = position;
    }
}
