package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import javax.swing.text.html.Option;
import java.sql.SQLOutput;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        run(args);
        System.out.println("Stop");
    }
    public static void run(String[] str){
        MoveDirection[] directions = OptionsParser.Parser(str);
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
