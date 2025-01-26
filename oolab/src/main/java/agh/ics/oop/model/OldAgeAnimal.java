package agh.ics.oop.model;

import static agh.ics.oop.model.OldAgeMovementBehavior.isMoveSkippedDueToAge;

public class OldAgeAnimal extends Animal {
    public OldAgeAnimal(Vector2d position, MapDirection orientation,
                        int defaultEnergySpawnedWith, int energyLossPerDay, int energyLossPerReproduction,
                        int energyNeededToReproduce, int genomeLength, int startIndexOfGenome,
                        Genome genome) {
        super(position, orientation, defaultEnergySpawnedWith, energyLossPerDay, energyLossPerReproduction,
                energyNeededToReproduce, genomeLength, startIndexOfGenome, genome);
    }

    @Override
    public void move(MoveValidator validator, int direction) {
        if (currentEnergy - energyLossPerDay > 0) {
            if (isMoveSkippedDueToAge(this)) {
                return;
            }

            super.move(validator, direction);
        } else setPassedAway(true);
    }
}
