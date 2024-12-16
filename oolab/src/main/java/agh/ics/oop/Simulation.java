package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable { //Runnable bo w SimulationEngine Thread(simulation) wymaga
    private final List<Animal> animals;
    private final List<MoveDirection> listOfMoves;
    private final WorldMap map;

    public Simulation(List<Vector2d> startPositions, List<MoveDirection> listOfMoves, WorldMap map) {
        this.animals = new ArrayList<>();
        this.listOfMoves = listOfMoves;
        this.map = map;

        for (Vector2d position : startPositions) {
            Animal animal = new Animal(position);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                System.out.println("Cannot place the animal: " + e.getMessage());
            }
        }
    }


    public void run() {
        for (int index = 0; index < listOfMoves.size(); index++) {
            int animalIndex = index % animals.size();
            Animal animal = animals.get(animalIndex);
            map.move(animal, listOfMoves.get(index));

            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.err.println("Symulacja przerwana: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }


    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals); //only view on animals List
    }
}

