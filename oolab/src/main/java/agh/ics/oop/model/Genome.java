package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Genome {
    private final int[] genome;

    public Genome(int genomeLength) {
        this.genome = new int[genomeLength];

        for (int i = 0; i < genomeLength; i++) {
            this.genome[i] = (int) (Math.random() * 8);
        }
    }

    public Genome(Genome g1, Genome g2, int g1Energy, int g2Energy) {
        int genomeLength = g1.getGenes().length;
        this.genome = new int[genomeLength];

        double genotypeDivisionRatio = (double) g1Energy / (g1Energy + g2Energy);

        boolean g1LeftSide = Math.random() < 0.5;

        int divisionPoint = (int) Math.round(genotypeDivisionRatio * genomeLength);

        if (g1LeftSide)  {
            for (int i = 0; i < divisionPoint; i++) {
                this.genome[i] = g1.getGenes()[i];
            }
            for (int i = divisionPoint; i < genomeLength; i++) {
                this.genome[i] = g2.getGenes()[i];
            }
        } else {
            for (int i = 0; i < divisionPoint; i++) {
                this.genome[i] = g2.getGenes()[i];
            }
            for (int i = divisionPoint; i < genomeLength; i++) {
                this.genome[i] = g1.getGenes()[i];
            }
        }

        mutate(this.genome);

    }

    public void mutate(int[] genome) {
        int genesToMutate = (int) (Math.random() * genome.length);
        List<Integer> indexes = new ArrayList<>();

        for ( int i = 0; i < genome.length; i++) {
            indexes.add(i);
        }


        Collections.shuffle(indexes);

        for ( int index : indexes) {
            genome[index] = (int) (Math.random() * 8);
        }
    }

    public int[] getGenes() {
        return genome;
    }
}
