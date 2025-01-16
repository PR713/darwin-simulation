package agh.ics.oop.model;

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
        this.genome = new int[g1.getGenes().length];

        for (int i = 0; i < genomeLength; i++) { //nazwa genomeLength
            this.genome[i] = (int) (Math.random() * 8); //genom a nie genotyp
        }
    }

    public int[] getGenes() {
        return genome;
    }
}
