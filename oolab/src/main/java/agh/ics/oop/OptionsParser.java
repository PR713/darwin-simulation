package agh.ics.oop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static List<MoveDirection> parser(String[] arguments) {
        List<MoveDirection> directions = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
            switch (arguments[i]) {
                case "f": directions.add(MoveDirection.FORWARD); break;
                case "b": directions.add(MoveDirection.BACKWARD); break;
                case "l": directions.add(MoveDirection.LEFT); break;
                case "r": directions.add(MoveDirection.RIGHT); break;
            }
        }
        return directions;
    }
}
