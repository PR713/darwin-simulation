package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Platform;

import java.io.FileInputStream;
import java.util.*;

public class Simulation implements Runnable {

    public boolean paused = false;
    private List<Animal> animals;
    private final WorldMap map;
    private final SimulationPresenter presenter;
    private final int simulationDuration;

    private FileInputStream fileInput;

    public Simulation(int animalCount, WorldMap map,
                      int genomeLength, int defaultEnergySpawnedWith, int energyLossPerDay,
                      int energyLossPerReproduction, int energyNeededToReproduce, int simulationDuration,
                      boolean isAging, SimulationPresenter presenter, boolean saveToFile) {
        this.animals = new ArrayList<>();
        this.map = map;
        this.presenter = presenter;
        this.simulationDuration = simulationDuration;

        RandomPositionGenerator randPosGenerator =
                new RandomPositionGenerator(map.getUpperRight().getX() + 1,
                        map.getUpperRight().getY() + 1, animalCount);

//        List<Vector2d> startPositions = new LinkedList<>();
//        startPositions.add(new Vector2d(3,3));

        for (Vector2d position : randPosGenerator) {
            createAndPlaceAnimal(position, genomeLength, defaultEnergySpawnedWith, energyLossPerDay,
                    energyLossPerReproduction, energyNeededToReproduce, isAging);
        }
    }

    private void createAndPlaceAnimal(Vector2d position, int genomeLength,
                                        int defaultEnergySpawnedWith, int energyLossPerDay,
                                        int energyLossPerReproduction,
                                        int energyNeededToReproduce, boolean isAging) {

        int startIndexOfGenome = (int) (Math.random() * genomeLength);
        Genome genome = new Genome(genomeLength);
        Animal animal = new Animal(position, MapDirection.fromNumericValue(genome.getGenes()[startIndexOfGenome]),
                defaultEnergySpawnedWith, energyLossPerDay,
                energyLossPerReproduction, energyNeededToReproduce,
                genomeLength, startIndexOfGenome, genome);
        try {
            map.place(animal);
            animals.add(animal);
        } catch (IncorrectPositionException e) {
            System.out.println("Cannot place the animal: " + e.getMessage());
        }
    }


    public void run() {
        for (int day = 1; day <= simulationDuration; day++) {
            map.deleteDeadAnimals();
            this.animals = getAnimals(); // jeśli się nowe urodziły
            for (Animal animal : animals) {
                animal.setHasAlreadyMoved(false);
                int direction = animal.getGenome().getGenes()[animal.getCurrentIndexOfGenome()];
                map.move(animal, MapDirection.fromNumericValue(direction));
                animal.incrementIndex();
            }
            try {
                do {
                    Thread.sleep(500);
                } while (paused);
            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
            map.updateEaten();
            map.updateAnimalsLifespan();
            map.updateAverageAliveAnimalsNumberOfChildren();
            map.updateAverageAliveAnimalsEnergy();
            map.updateReproduction();
            map.updateMostPopularGenome();
            map.addGrassTufts();
            if (!map.getAllAnimals().isEmpty())
                System.out.println(map.getAllAnimals().getFirst().getPosition());

            Platform.runLater(presenter::drawMap);
        }
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(map.getAllAnimals()); //only view on animals List
    }
}

