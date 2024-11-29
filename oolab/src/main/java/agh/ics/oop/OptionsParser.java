package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static List<MoveDirection> parseToMoveDirection(String[] arguments) {
        List<MoveDirection> directions = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
            switch (arguments[i]) {
                case "f":
                    directions.add(MoveDirection.FORWARD);
                    break;
                case "b":
                    directions.add(MoveDirection.BACKWARD);
                    break;
                case "l":
                    directions.add(MoveDirection.LEFT);
                    break;
                case "r":
                    directions.add(MoveDirection.RIGHT);
                    break;
                default:
                    throw new IllegalArgumentException(arguments[i] + " is not legal move specification");
            } //sam się przerwie piętro wyżej jeśli nie obsłużymy a nie musimy bo unchecked
        }

        return directions;
    }
}
