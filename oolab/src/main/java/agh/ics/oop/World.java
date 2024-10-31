package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.MapDirection;

import javax.swing.text.html.Option;
import java.sql.SQLOutput;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        MoveDirection[] directions = OptionsParser.parser(args);
        run(directions);
        System.out.println("Stop");

        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        MapDirection map = MapDirection.NORTH;
        System.out.println(map.toUnitVector());
        System.out.println(map.next());
        System.out.println(map.previous());
        System.out.println(map);



    }
    private static void run(MoveDirection[] directions){
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
