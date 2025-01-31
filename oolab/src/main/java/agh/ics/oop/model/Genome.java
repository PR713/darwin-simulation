package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Arrays;
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


        boolean leftSide = Math.random() < 0.5;

        int[] tempGenome = new int[genomeLength];

        Genome strongerGenome;
        Genome weakerGenome;
        int strongerEnergy;

        if (g1Energy >= g2Energy) {
            strongerGenome = g1;
            weakerGenome = g2;
            strongerEnergy = g1Energy;
        } else {
            strongerGenome = g2;
            weakerGenome = g1;
            strongerEnergy = g2Energy;
        }

        double genotypeDivisionRatio = (double) strongerEnergy / (g1Energy + g2Energy);

        int divisionPoint = (int) Math.floor(genotypeDivisionRatio * (genomeLength - 1));


        if (leftSide) {
            for (int i = 0; i < divisionPoint; i++) {
                tempGenome[i] = strongerGenome.getGenes()[i];
            }
            for (int i = divisionPoint; i < genomeLength; i++) {
                tempGenome[i] = weakerGenome.getGenes()[i];
            }
        } else {
            for (int i = 0; i < divisionPoint; i++) {
                tempGenome[i] = weakerGenome.getGenes()[i];
            }
            for (int i = divisionPoint; i < genomeLength; i++) {
                tempGenome[i] = strongerGenome.getGenes()[i];
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


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Genome genome1 = (Genome) other;
        return Arrays.equals(genome1.genome, this.genome);

    }


    @Override
    public int hashCode() {
        return Arrays.hashCode(genome);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int gene : genome) {
            sb.append(gene);
        }
        return sb.toString();
    }
}
