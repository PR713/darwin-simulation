package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;

import java.util.List;

import static agh.ics.oop.OptionsParser.parseToMoveDirection;

public class World {
    public static void main(String[] args) {
//        List<MoveDirection> directions = parseToMoveDirection(args);
//        //AbstractWorldMap map1 = new GrassField(5);
//        //AbstractWorldMap map2 = new RectangularMap(5,5);
//        List<Vector2d> positions = List.of(new Vector2d(1,1), new Vector2d(2,4),new Vector2d(1,3));
//        //sekwencyjnie place map1, place map2 potem move map1, move map2 dla runSync()
//
//        ConsoleMapDisplay display = new ConsoleMapDisplay();
//        List<Simulation> simulations = new ArrayList<>();
//        for (int i = 0; i < 3000; i++) {
//            AbstractWorldMap map = (i % 2 == 1) ? new GrassField(5) : new RectangularMap(5,5);
//            map.addObserver(display);
//            simulations.add(new Simulation(positions, directions, map));
//        }
//
//        SimulationEngine engine = new SimulationEngine(simulations);
//        //engine.runSync()
//        //engine.runAsync();
//        engine.runAsyncInThreadPool();
//        try {engine.awaitSimulationsEnd();
//        } catch (InterruptedException e) {
//            System.err.println("Oczekiwanie na zakończenie symulacji zostało przerwane.");
//        }
//        System.out.println("System zakończył działanie");




        Application.launch(SimulationApp.class, args);
    }


    private static void run(List<MoveDirection> directions){
        for (MoveDirection dir : directions) {
            switch (dir) {
                case MoveDirection.FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case MoveDirection.BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case MoveDirection.LEFT -> System.out.println("Zwierzak idzie w lewo");
                case MoveDirection.RIGHT -> System.out.println("Zwierzak idzie w prawo");
            }
        }
    }
}
