package agh.ics.oop.model;

import java.io.*;

public record Config(String name,
                     int sizeX,
                     int sizeY,
                     int initialPopulation,
                     int initialGrassCount,
                     int grassEnergy,
                     int dailyGrassGrowth,
                     int initialAnimalEnergy,
                     int reproductionMinEnergy,
                     int reproductionConsumedEnergy,
                     int minMutations,
                     int maxMutations,
                     int genomeLength,
                     int dailyEnergyUsage,
                     int simulationDuration,
                     boolean aging,
                     boolean wildOwlBear) implements Serializable {

    static final String filePath = "configs.dat";


    public static Config[] loadConfigs() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Config[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Config[0];
        }
    }

    public static void saveConfigs(Config[] configs) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(configs);
        } catch (IOException e) {
            //Brak dostępu
        }
    }
}

