package agh.ics.oop.model;

public class Genome {
    private final int[] genotype;

    public Genome(int genotypeLength) {
        this.genotype = new int[genotypeLength];

        for (int i = 0; i < genotypeLength; i++) { //nazwa genomeLength
            this.genotype[i] = (int) (Math.random() * 8); //genom a nie genotyp
        }
    }

    public Genome(Genome g1, Genome g2, g1Ene) {
        this.genotype = new int[genotypeLength];

        for (int i = 0; i < genotypeLength; i++) { //nazwa genomeLength
            this.genotype[i] = (int) (Math.random() * 8); //genom a nie genotyp
        }
    }

    public int[] getGenes() {
        return genotype;
    }

    // EWENTUALNIE NOWA KLASA!!!
    public Genome cross(Animal animal1, Animal animal2) {
        int[] newGenes = new int[genotype.length];
        int[] animal1Genes = animal1.getGenome();
        int[] animal2Genes = animal2.getGenome();
        int animal1Energy = animal1.getEnergy();
        int animal2Energy = animal2.getEnergy();
        //animal 3 nazwa genotyp
        //...

        return new Genome(newGenes);
    }

//    public Gene mutate() {
//        int[] newGenes = genotype.clone();
//        int mutationPoint = (int) (Math.random() * genotype.length);
//        newGenes[mutationPoint] = (int) (Math.random() * 8);
//
//        return new Gene(newGenes);
//    }
}
