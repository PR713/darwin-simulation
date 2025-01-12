package agh.ics.oop.model;

public class WildOwlBearMap extends AbstractWorldMap {

    private final Vector2d owlBearAreaLowerLeft;
    private final Vector2d owlBearAreaUpperRight;
    private final boolean oldAgeBehaviour = false;

    public WildOwlBearMap(int height, int width,
                          Vector2d owlBearAreaLowerLeft, Vector2d owlBearAreaUpperRight) {
        super(height, width);
        this.owlBearAreaLowerLeft = owlBearAreaLowerLeft;
        this.owlBearAreaUpperRight = owlBearAreaUpperRight;
    }


    @Override
    public Boundary getCurrentBounds() {
        return null; //?? to do?
    }
}
