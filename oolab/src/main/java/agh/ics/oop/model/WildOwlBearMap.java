package agh.ics.oop.model;

public class WildOwlBearMap extends AbstractWorldMap {

    private final Vector2d owlBearAreaLowerLeft;
    private final Vector2d owlBearAreaUpperRight;

    public WildOwlBearMap(Vector2d lowerLeft, Vector2d upperRight,
                          Vector2d owlBearAreaLowerLeft, Vector2d owlBearAreaUpperRight) {
        super(lowerLeft, upperRight);
        this.owlBearAreaLowerLeft = owlBearAreaLowerLeft;
        this.owlBearAreaUpperRight = owlBearAreaUpperRight;
    }


    @Override
    public Boundary getCurrentBounds() {
        return null; //?? to do?
    }
}
