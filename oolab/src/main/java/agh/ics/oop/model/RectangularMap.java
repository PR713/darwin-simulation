package agh.ics.oop.model;

public class RectangularMap extends AbstractWorldMap  {

    public RectangularMap(int width, int height) {
        super(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight) && position.follows(lowerLeft) && (!super.isOccupied(position)));
    }


    @Override
    public Boundary getCurrentBounds(){
        return new Boundary(lowerLeft, upperRight);
    }
}
