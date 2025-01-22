package agh.ics.oop.model

import Animal
import WorldElement
import WorldMap
import agh.ics.oop.extensions.randomFreePosition
import agh.ics.oop.extensions.randomPosition
import org.example.Vector2d

class BouncyMap(private val width: Int, private val height: Int) : WorldMap {
    private val animals = mutableMapOf<Vector2d, Animal>()

    override fun canMoveTo(position: Vector2d): Boolean {
        return position.x in 0 until width && position.y in 0 until height
    }

    override fun objectAt(position: Vector2d): WorldElement? {
        return animals[position]
    }

    //place(Animal) aktualizuje pozycję zwierzęcia na mapie, a orientacja nie ulega zmianie,
    //więc nie trzeba również move w Animal
    override fun place(animal: Animal) {
        val currentPosition = animal.getPosition()
        val existingAnimal = animals[currentPosition]

        if (existingAnimal == null) {
            animals[currentPosition] = animal
        } else {
            val newPosition = animals.randomFreePosition(Vector2d(width, height)) ?: run {
                val positionToRemove = animals.randomPosition() ?: return //Operator Elvis
                animals.remove(positionToRemove)
                positionToRemove //run wbudowane - zwraca wartość ostatniej instrukcji w bloku,
                // czyli positionToRemove
            }

            animals[newPosition] = animal
        }
    }

    override fun isOccupied(position: Vector2d): Boolean {
        return animals.containsKey(position)
    }

    override fun getElements(): List<WorldElement> {
        return animals.values.toList()
    }

}
