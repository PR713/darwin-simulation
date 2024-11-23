package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int updateCount = 0;

    @Override
    public void mapChanged(WorldMap map, String message) {
        updateCount++;
        System.out.printf(message);
        System.out.println(map.toString());
        System.out.printf("Map update %d ", updateCount);
    }
}
