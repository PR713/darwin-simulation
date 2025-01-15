package agh.ics.oop.model;

import java.util.List;

public class WildOwlBearMap extends AbstractWorldMap {

    private final Vector2d owlBearAreaLowerLeft;
    private final Vector2d owlBearAreaUpperRight;
    private final boolean oldAgeBehaviour = false;
    protected WildOwlBear wildOwlBear;

    public WildOwlBearMap(int height, int width,
                          Vector2d owlBearAreaLowerLeft, Vector2d owlBearAreaUpperRight, WildOwlBear wildOwlBear) {
        super(height, width);
        this.wildOwlBear = wildOwlBear;
        this.owlBearAreaLowerLeft = owlBearAreaLowerLeft;
        this.owlBearAreaUpperRight = owlBearAreaUpperRight;
    }


    @Override
    public Boundary getCurrentBounds() {
        return null; //?? to do?
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
}
