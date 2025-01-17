package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileMapDisplay implements MapChangeListener {
    private final UUID map_id;

    public FileMapDisplay(UUID mapId) {
        this.map_id = mapId;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String filename = map_id + ".log";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))){
            writer.write("Message: " + message + "\n");
            writer.write("Map state:\n" + worldMap + "\n");
            writer.write("-----------------------------\n");
        } catch (IOException e) {
            System.out.println("Error while saving to file: " + filename + ":" + e.getMessage());
        }
    }
}
