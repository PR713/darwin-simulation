package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import javax.swing.text.html.Option;
import java.sql.SQLOutput;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        MoveDirection[] directions = OptionsParser.parser(args);
        run(directions);
        System.out.println("Stop");
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
