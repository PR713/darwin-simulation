package agh.ics.oop.model;

import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleMapDisplay implements MapChangeListener {
    private final AtomicInteger updateCount = new AtomicInteger();

    @Override
    public void mapChanged(WorldMap map, String message) {
        synchronized (System.out) {
            System.out.println(message);
            System.out.println(map);
            System.out.printf("Map %s update %d %n%n%n", map.getId(), updateCount.incrementAndGet());
        }
    }
}
