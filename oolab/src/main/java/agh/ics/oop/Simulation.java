package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Platform;

import java.io.*;
import java.util.*;

public class Simulation implements Runnable {

    public boolean paused = false;
    private boolean quit = false;

    private final AbstractWorldMap map;
    private final SimulationPresenter presenter;
    private final int simulationDuration;

    private int day;

    private FileOutputStream fileInput;
    private PrintStream printStream;

    public Simulation(int animalCount, AbstractWorldMap map,
                      int genomeLength, int defaultEnergySpawnedWith, int energyLossPerDay,
                      int energyLossPerReproduction, int energyNeededToReproduce, int simulationDuration,
                      boolean isAging, SimulationPresenter presenter, boolean saveToFile) {
        this.map = map;
        this.presenter = presenter;
        this.simulationDuration = simulationDuration;

        RandomPositionGenerator randPosGenerator =
                new RandomPositionGenerator(map.getUpperRight().getX() + 1,
                        map.getUpperRight().getY() + 1, animalCount);

        if (saveToFile) {
            initializeFileStream();
        }

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
        Animal animal;
        if (isAging) {
            animal = new OldAgeAnimal(position, MapDirection.fromNumericValue(genome.getGenes()[startIndexOfGenome]),
                    defaultEnergySpawnedWith, energyLossPerDay,
                    energyLossPerReproduction, energyNeededToReproduce,
                    startIndexOfGenome, genome);
        } else {
            animal = new Animal(position, MapDirection.fromNumericValue(genome.getGenes()[startIndexOfGenome]),
                    defaultEnergySpawnedWith, energyLossPerDay,
                    energyLossPerReproduction, energyNeededToReproduce,
                    startIndexOfGenome, genome);
        }

        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            System.out.println("Cannot place the animal: " + e.getMessage());
        }
    }

    private void initializeFileStream() {
        try {
            UUID id = UUID.randomUUID();
            fileInput = new FileOutputStream(id.toString() + "_log.csv", true);
            printStream = new PrintStream(fileInput);
            logHeaders();
        } catch (FileNotFoundException exception) {
            fileInput = null;
            //Brak dostepu, there's nothing we can do :(
        }
    }

    public void run() {
        for (int day = 1; day <= simulationDuration; day++) {
            this.day = day;

            map.deleteDeadAnimals();

            map.moveAnimals();

            map.updateEaten();

            map.updateReproduction();
            map.updateAverageAliveAnimalsNumberOfChildren();
            map.updateAverageAliveAnimalsEnergy();
            map.updateMostPopularGenome();
            map.updateAnimalsLifespan();

            map.addGrassTufts();

            if (printStream != null)
                logStats();

            if (presenter != null)
                Platform.runLater(presenter::drawMap); //Tu mogl by byc observer pattern ale jest to nie potrzebne bo jest tylko jeden przypadek uzycia

            if (quit)
                return;

            handleTick(); //jeśli pod drawMap to stan na koniec dnia (z martwymi też)
            //jeśli np pod deleteDeadAnimals to jeśli zrobimy stop to martwych już nie będzie
            //bo platform.runlater już ma w kolejce drawMap - tak jakby dzięki platform.runlater
            //to się rysuje w momencie zrobienia handleTick bo wtedy ma czas na to :)
            //kliknięcie na animala też powoduje drawMap
        }
    }

    private void handleTick() {
        try {
            do {
                Thread.sleep(100);
            } while (paused);
        } catch (InterruptedException e) {
            System.err.println("Symulacja przerwana: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void logHeaders() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Day,");
        stringBuilder.append("Animal Count,");
        stringBuilder.append("Grass Count,");
        stringBuilder.append("Empty Cells,");
        stringBuilder.append("Best Genome,");
        stringBuilder.append("Mean Animal Energy,");
        stringBuilder.append("Mean Life Length,");
        stringBuilder.append("Mean Child Count,");
        printStream.println(stringBuilder);
    }

    private void logStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(day).append(",");
        stringBuilder.append(map.getAllAnimals().size()).append(",");
        stringBuilder.append(map.getAllGrassTufts().size()).append(",");
        stringBuilder.append(map.getEmptyPositionsCount()).append(",");
        stringBuilder.append(map.getMostPopularGenome()).append(",");
        stringBuilder.append(map.getAverageAliveAnimalsEnergy()).append(",");
        stringBuilder.append(map.getAverageDeadAnimalsAge()).append(",");
        stringBuilder.append(map.getAverageAliveAnimalsNumberOfChildren()).append(",");
        printStream.println(stringBuilder);
    }

    public void disposeSimulation() {
        quit = true;

        if (fileInput == null)
            return;

        printStream.close();
        try {
            fileInput.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileInput = null;
        printStream = null;
    }

    public int getDay() {
        return day;
    }
}

