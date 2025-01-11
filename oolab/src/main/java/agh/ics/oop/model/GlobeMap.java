package agh.ics.oop.model;

public class GlobeMap extends AbstractWorldMap {

    public GlobeMap(Vector2d lowerLeft, Vector2d upperRight) {
        super(lowerLeft, upperRight);
    }


    @Override
    public Boundary getCurrentBounds() {
        return null; //TO DO?
    }
}
