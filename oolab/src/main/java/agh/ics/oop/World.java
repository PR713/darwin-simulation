package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) {
        List<MoveDirection> directions = OptionsParser.parser(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        WorldMap map = new RectangularMap(5,5);
        //obiekt map typu WorldMap z implementacją w RectangularMap
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();
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
