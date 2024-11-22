package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {
    public static void main(String[] args) throws IncorrectPositionException {
        GrassField map = new GrassField(1);
        Animal animal = new Animal(new Vector2d(1,1));
        Animal animal1 = new Animal(new Vector2d(1,0));
        map.place(animal);
        map.place(animal1);

        System.out.println(map);
        //jeśli * trafi na ^ to rysuje ^ OK
        Vector2d vector2d = new Vector2d(1,2);
        System.out.println(vector2d);
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
