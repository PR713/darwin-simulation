package agh.ics.oop.model;

import java.util.ArrayList;
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

    public Genome(Genome g1, Genome g2, int g1Energy, int g2Energy,
                  int minNumberOfMutations, int maxNumberOfMutations) {
        int genomeLength = g1.getGenes().length;

        double genotypeDivisionRatio = (double) g1Energy / (g1Energy + g2Energy);

        boolean g1LeftSide = Math.random() < 0.5;

        int divisionPoint = (int) Math.round(genotypeDivisionRatio * genomeLength);

        int[] tempGenome = new int[genomeLength];

        if (g1LeftSide) {
            for (int i = 0; i < divisionPoint; i++) {
                tempGenome[i] = g1.getGenes()[i];
            }
            for (int i = divisionPoint; i < genomeLength; i++) {
                tempGenome[i] = g2.getGenes()[i];
            }
        } else {
            for (int i = 0; i < divisionPoint; i++) {
                tempGenome[i] = g2.getGenes()[i];
            }
            for (int i = divisionPoint; i < genomeLength; i++) {
                tempGenome[i] = g1.getGenes()[i];
            }
        }

        this.genome = mutate(tempGenome, minNumberOfMutations, maxNumberOfMutations);
    }

    public int[] mutate(int[] genome, int minNumberOfMutations, int maxNumberOfMutations) {
        int range = maxNumberOfMutations - minNumberOfMutations + 1;
        int genesToMutate = minNumberOfMutations + (int) (Math.random() * range);
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < genome.length; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i = 0; i < genesToMutate; i++) {
            int index = indexes.get(i);
            genome[index] = (int) (Math.random() * 8);
        }
        return genome;
    }

    public int[] getGenes() {
        return genome;
    }
}
