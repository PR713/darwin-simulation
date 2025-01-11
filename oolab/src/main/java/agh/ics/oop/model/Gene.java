package agh.ics.oop.model;

public class Gene {
    private final int[] genotype;

    public Gene(int genotypeLength) {
        genotype = new int[genotypeLength];

        for (int i = 0; i < genotypeLength; i++) {
            genotype[i] = (int) (Math.random() * 8);
        }
    }

    public int[] getGenes() {
        return genotype;
    }

    public int getGene(int index) {
        return genotype[index];
    }

//    public Gene cross(Gene other) {
//        int[] newGenes = new int[genotype.length];
//        int crossPoint = (int) (Math.random() * genotype.length);
//
//        for (int i = 0; i < genotype.length; i++) {
//            newGenes[i] = i < crossPoint ? genotype[i] : other.getGene(i);
//        }
//
//        return new Gene(newGenes);
//    }

//    public Gene mutate() {
//        int[] newGenes = genotype.clone();
//        int mutationPoint = (int) (Math.random() * genotype.length);
//        newGenes[mutationPoint] = (int) (Math.random() * 8);
//
//        return new Gene(newGenes);
//    }
}
