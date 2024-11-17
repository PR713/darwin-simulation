package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

public class RectangularMap extends AbstractWorldMap  {
    private final MapVisualizer visualizer;

    public RectangularMap(int width, int height) {
        super(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        this.visualizer = new MapVisualizer(this);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.precedes(upperRight) && position.follows(lowerLeft) && (!isOccupied(position)));
    }


    @Override
    public String toString(){
        return visualizer.draw(lowerLeft,upperRight);
    }
}
