package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Platform;

import java.util.*;

public class Simulation implements Runnable { //Runnable bo w SimulationEngine Thread(simulation) wymaga
    private List<Animal> animals;
    private final WorldMap map;
    private final SimulationPresenter presenter;
    private final int simulationDuration;

    public Simulation(List<Vector2d> startPositions, WorldMap map,
                      int genomeLength, int defaultEnergySpawnedWith, int energyLossPerDay,
                      int energyLossPerReproduction, int energyNeededToReproduce, int simulationDuration,
                      boolean isAging, SimulationPresenter presenter) {
        this.animals = new ArrayList<>();
        this.map = map;
        this.presenter = presenter;
        this.simulationDuration = simulationDuration;

        for (Vector2d position : startPositions) {
            int startIndexOfGenome = (int) (Math.random() * genomeLength);
            Genome genome = new Genome(genomeLength);
            Animal animal = new Animal(position, MapDirection.fromNumericValue(genome.getGenes()[startIndexOfGenome]),
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
            map.updateAverageAliveAnimalsEnergy();
            this.animals = getAnimals(); // jeśli się nowe urodziły
            for (Animal animal : animals) {
                animal.setHasAlreadyMoved(false);
                int direction = animal.getGenome().getGenes()[animal.getCurrentIndexOfGenome()];
                map.move(animal, MapDirection.fromNumericValue(direction));
                animal.incrementIndex();
            }
            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            System.out.println("Sim update");
            map.updateEaten();
            System.out.println("1");
            map.updateAnimalsLifespan();
            System.out.println("2");
            map.updateReproduction();
            System.out.println("3");
            map.addGrassTufts();
            System.out.println("4");
            System.out.println("Zwierze count: " + map.getAllAnimals().size());
            if (!map.getAllAnimals().isEmpty())
                System.out.println(map.getAllAnimals().getFirst().getPosition());
            Platform.runLater(() -> presenter.drawMap(""));

        }


    }


    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(map.getAllAnimals()); //only view on animals List
    }
}

