package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap  {
    private final Boundary boundaries;

    public RectangularMap(int width, int height) {
        super(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        boundaries = new Boundary(lowerLeft, upperRight);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight) && position.follows(lowerLeft) && (!super.isOccupied(position)));
    }


    @Override
    public Boundary getCurrentBounds(){
        return boundaries;
    }
}
