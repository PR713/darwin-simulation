package agh.ics.oop.model;

public class OldAgeGenes extends Gene {

    private final int oldAgeBeginsOn;

    public OldAgeGenes(int genotypeLength, int oldAgeBeginsOn) {
        super(genotypeLength);
        this.oldAgeBeginsOn = oldAgeBeginsOn;
    }

    public int getOldAgeBeginsOn() {
        return oldAgeBeginsOn;
    }

}
