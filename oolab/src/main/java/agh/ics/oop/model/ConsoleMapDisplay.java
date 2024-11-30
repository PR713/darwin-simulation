package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {
    private int updateCount = 0;

    @Override
    public void mapChanged(WorldMap map, String message) {
        updateCount++;
        System.out.println(message);
        System.out.println(map);
        System.out.printf("Map %s update %d %n%n%n", map.getId(), updateCount);
    }
}
