package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenomeTest {

    @Test
    public void testGenomeConstructor() {
        int genomeLength = 10;
        Genome genome = new Genome(genomeLength);
        int[] genes = genome.getGenes();

        assertEquals(genomeLength, genes.length);
        for (int gene : genes) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }


    @Test
    public void testGenomeCombinationAndMutation() {
        int genomeLength = 10;
        Genome g1 = new Genome(genomeLength);
        Genome g2 = new Genome(genomeLength);

        int g1Energy = 40;
        int g2Energy = 60;
        int minMutations = 1;
        int maxMutations = 3;

        Genome childGenome = new Genome(g1, g2, g1Energy, g2Energy, minMutations, maxMutations);

        int[] childGenes = childGenome.getGenes();
        assertEquals(genomeLength, childGenes.length);

        int mutationLeftCount = 0;
        int genomeDivisor = (int) Math.floor((double) 40/(60+40) * (genomeLength - 1));

        for (int i = 0; i < childGenes.length; i++) {
            if (i < genomeDivisor ) {
                if (childGenes[i] != g1.getGenes()[i]) {
                    mutationLeftCount++;
                }
            } else {
                if (childGenes[i] != g2.getGenes()[i]) {
                    mutationLeftCount++;
                }
            }
        }

        int mutationRightCount = 0;
        for (int i = 0; i < childGenes.length; i++) {
            if (i < genomeDivisor ) {
                if (childGenes[i] != g2.getGenes()[i]) {
                    mutationRightCount++;
                }
            } else {
                if (childGenes[i] != g1.getGenes()[i]) {
                    mutationRightCount++;
                }
            }
        }

        System.out.println(mutationCount);
        System.out.println(childGenome);
        System.out.println(g1);
        System.out.println(g2);
        assertTrue(mutationCount >= 0 && mutationCount <= maxMutations);
    }
}
