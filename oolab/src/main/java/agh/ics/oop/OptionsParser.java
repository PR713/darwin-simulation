package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static List<MoveDirection> parser(String[] arguments) {
        List<MoveDirection> directions = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
             try {
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
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }

        }

        return directions;
    }
}
