package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static List<MoveDirection> parseToMoveDirection(String[] arguments) {
        return Stream.of(arguments).map(arg -> {
            return switch (arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "l" -> MoveDirection.LEFT;
                case "r" -> MoveDirection.RIGHT;
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            }; //sam się przerwie piętro wyżej jeśli nie obsłużymy a nie musimy bo unchecked
        }).collect(Collectors.toList());
    }
}

