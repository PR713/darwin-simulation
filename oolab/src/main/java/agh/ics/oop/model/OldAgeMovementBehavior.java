package agh.ics.oop.model;


public class OldAgeMovementBehavior {

    public static boolean isMoveSkippedDueToAge(Animal animal) {
        double maxChance = 0.8;
        int animalAge = animal.getNumberOfDaysAlive();
        return Math.random() < (1 - Math.exp(-0.001 * animalAge)) * maxChance;
        //rzadko, rzadko -> rośnie :), limes = 0.8
        //TODO
    }

}
