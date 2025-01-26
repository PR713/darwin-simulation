package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        for (int gene : childGenes) {
            assertTrue(gene >= 0 && gene < 8);
        }

        int mutationLeftCount = 0;
        int genomeDivisor = (int) Math.floor((double) 60/(60+40) * (genomeLength - 1));
        //left side stronger's one
        for (int i = 0; i < childGenes.length; i++) {
            if (i < genomeDivisor ) {
                if (childGenes[i] != g2.getGenes()[i]) {
                    mutationLeftCount++;
                }
            } else {
                if (childGenes[i] != g1.getGenes()[i]) {
                    mutationLeftCount++;
                }
            }
        }


        int mutationRightCount = 0;
        //right side stronger's one
        for (int i = 0; i < childGenes.length; i++) {
            if (i < genomeDivisor ) {
                if (childGenes[i] != g1.getGenes()[i]) {
                    mutationRightCount++;
                }
            } else {
                if (childGenes[i] != g2.getGenes()[i]) {
                    mutationRightCount++;
                }
            }
        }

        int mutationCount = Math.min(mutationLeftCount, mutationRightCount);

        assertTrue(mutationCount >= 0 && mutationCount <= maxMutations);
    }


    @Test
    public void testEquals(){
        Genome genome1 = new Genome(50);
        Genome genome2 = new Genome(50); //szansa że takie same 1/8^50...

        assertTrue(genome1.equals(genome1));
        assertFalse(genome1.equals(genome2));
        assertFalse(genome1.equals(null));
    }


    @Test
    public void testToString(){
        Genome genome = new Genome(10);
        int[] genes = genome.getGenes();
        String genomeString = genome.toString();

        assertEquals(10, genomeString.length());

        for (int i = 0; i < 10; i++) {
            assertEquals(Character.getNumericValue(genomeString.charAt(i)), genes[i]);
        }
    }
}
