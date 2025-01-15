package agh.ics.oop.model;

public class Gene {
    private final int[] genotype;

    public Gene(int genotypeLength) {
        this.genotype = new int[genotypeLength];

        for (int i = 0; i < genotypeLength; i++) {
            this.genotype[i] = (int) (Math.random() * 8);
        }
    }

    public int[] getGenes() {
        return genotype;
    }

    // EWENTUALNIE NOWA KLASA!!!
    public Gene cross(Animal animal1, Animal animal2) {
        int[] newGenes = new int[genotype.length];
        int[] animal1Genes = animal1.getGenome();
        int[] animal2Genes = animal2.getGenome();
        int animal1Energy = animal1.getEnergy();
        int animal2Energy = animal2.getEnergy();
        //...

        return new Gene(newGenes);
    }

//    public Gene mutate() {
//        int[] newGenes = genotype.clone();
//        int mutationPoint = (int) (Math.random() * genotype.length);
//        newGenes[mutationPoint] = (int) (Math.random() * 8);
//
//        return new Gene(newGenes);
//    }
}
