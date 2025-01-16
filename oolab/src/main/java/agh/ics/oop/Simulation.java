package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;

import java.util.*;

public class Simulation implements Runnable { //Runnable bo w SimulationEngine Thread(simulation) wymaga
    private List<Animal> animals;
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
            Genome genome = new Genome(genomeLength);
            Animal animal = new Animal(position, MapDirection.fromNumericValue(startIndexOfGenome),
                    defaultEnergySpawnedWith, energyLossPerDay,
                    energyLossPerReproduction, energyNeededToReproduce,
                    genomeLength, startIndexOfGenome, isAging, genome);
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
            map.deleteDeadAnimals();
            this.animals = getAnimals(); // jeśli się nowe urodziły
            for (Animal animal : animals) {
                animal.setHasAlreadyMoved(false);
                int direction = animal.getGenome().getGenes()[animal.getCurrentIndexOfGenome()];
                animal.move(map, direction);
                animal.incrementIndex();
            }
            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            map.updateEaten();
            map.updateReproduction();
            map.addGrassTufts();
        }


    }


    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(map.getAllAnimals()); //only view on animals List
    }
}

