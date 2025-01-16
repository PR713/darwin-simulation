package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;

import java.util.*;

public class Simulation implements Runnable { //Runnable bo w SimulationEngine Thread(simulation) wymaga
    private final List<AbstractAnimal> animals;
    private final WorldMap map;
    private final int simulationDuration;
    public Simulation(List<Vector2d> startPositions, WorldMap map,
            int genomeLength, int defaultEnergySpawnedWith, int energyLossPerDay,
                      int energyLossPerReproduction, int energyNeededToReproduce, int simulationDuration, boolean isAging) {
        this.animals = new ArrayList<>();
        this.map = map;
        this.simulationDuration = simulationDuration;

        for (Vector2d position : startPositions) {
            MapDirection orientation = MapDirection.randomOrientation();
            int startIndexOfGenome = (int) (Math.random() * genomeLength);
            AbstractAnimal animal = new Animal(position, MapDirection.fromNumericValue(startIndexOfGenome),
                    defaultEnergySpawnedWith, energyLossPerDay,
                    energyLossPerReproduction, energyNeededToReproduce,
                    genomeLength, startIndexOfGenome, isAging);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.out.println("Cannot place the animal: " + e.getMessage());
            }
        }
    }


    public void run() {
        for (int day = 1; day <= simulationDuration; day++) {

            for (AbstractAnimal animal : animals) {
                int direction = animal.getGenome()[animal.getCurrentIndexOfGenome()];
                animal.move(map, direction);
                animal.incrementIndex();
            }
            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            //updateMap() -> owlBear je zwierzaki, animal trawę
        }
    }


    public List<AbstractAnimal> getAnimals() {
        return Collections.unmodifiableList(animals); //only view on animals List
    }
}

