package agh.ics.oop.model;


public class AnimalWithGenome {

    AbstractAnimal animal;
    int[] genome;

    public AnimalWithGenome(AbstractAnimal animal, int[] genome) {
        this.animal = animal;
        this.genome = genome;
    }
}
