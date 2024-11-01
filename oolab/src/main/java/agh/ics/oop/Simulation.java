package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<Animal> animals;
    private final List<MoveDirection> listOfMoves;

    public Simulation(List<Vector2d> startPositions, List<MoveDirection> listOfMoves) {
        this.animals = new ArrayList<>();
        this.listOfMoves = listOfMoves;
        for (Vector2d position : startPositions) {
            animals.add(new Animal(position));
        }
    }

    public void run() {
        for (int index = 0; index < listOfMoves.size(); index++) {
            int animalIndex = index % animals.size();
            animals.get(animalIndex).move(listOfMoves.get(index));
            System.out.printf("Zwierzę %d: %s%n",animalIndex, animals.get(animalIndex).toString());
            }
        }

    }

