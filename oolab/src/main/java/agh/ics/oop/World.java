package agh.ics.oop;

import agh.ics.oop.exceptions.IncorrectPositionException;
import agh.ics.oop.model.*;

import java.util.List;

import static agh.ics.oop.OptionsParser.parseToMoveDirection;

public class World {
    public static void main(String[] args) throws IncorrectPositionException {
        List<MoveDirection> directions = parseToMoveDirection(args);
        AbstractWorldMap map1 = new GrassField(5);
        AbstractWorldMap map2 = new RectangularMap(5,5);
        List<Vector2d> positions = List.of(new Vector2d(1,1), new Vector2d(2,4),new Vector2d(1,3));
        map1.addObserver(new ConsoleMapDisplay());
        map2.addObserver(new ConsoleMapDisplay());
        //sekwencyjnie place map1, place map2 potem move map1, move map2
        Simulation simulation1 = new Simulation(positions, directions,  map1);
        Simulation simulation2 = new Simulation(positions, directions,  map2);
        List<Simulation> simulations = List.of(simulation1, simulation2);
        SimulationEngine engine = new SimulationEngine(simulations);
        //engine.runSync()
        engine.runAsync();
        engine.awaitSimulationsEnd();
        System.out.println("System zakończył działanie");
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
