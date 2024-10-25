package agh.ics.oop;

import java.lang.reflect.Array;
import java.util.ArrayList;

import agh.ics.oop.model.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] parser(String[] arguments) {
        ArrayList<MoveDirection> directions = new ArrayList<MoveDirection>();
        for (int i = 0; i < arguments.length; i++) {
            switch (arguments[i]) {
                case "f": directions.add(MoveDirection.FORWARD); break;
                case "b": directions.add(MoveDirection.BACKWARD); break;
                case "l": directions.add(MoveDirection.LEFT); break;
                case "r": directions.add(MoveDirection.RIGHT); break;
            }
        }
        return directions.toArray(new MoveDirection[0]);
    }
}
