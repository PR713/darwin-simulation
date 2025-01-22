import agh.ics.oop.model.BouncyMap
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.example.*

class BouncyMapTest : BehaviorSpec({
    //test 1
    given("An empty BouncyMap") {
        val map = BouncyMap(5, 5)

        `when`("Placing an animal within map boundaries") {
            val animal = Animal(Vector2d(2, 2), MapDirection.NORTH)
            map.place(animal)

            then("the animal should appear on the map at the given position") {
                map.objectAt(Vector2d(2, 2)) shouldBe animal
            }
        }

        `when`("placing an animal outside map boundaries") {
            val animal = Animal(Vector2d(10, 10), MapDirection.NORTH)

            then("the position should be invalid for placement") {
                map.canMoveTo(animal.getPosition()) shouldBe false
            }
        }
    }

    //test 2
    given("A BouncyMap with one animal already placed") {
        val map = BouncyMap(5, 5)
        val animal1 = Animal(Vector2d(2, 2), MapDirection.NORTH)
        map.place(animal1)

        `when`("placing another animal on the same position") {
            val animal2 = Animal(Vector2d(2, 2), MapDirection.SOUTH)
            map.place(animal2)

            then("the new animal should be bounced to a random free position") {
                val animal2Position = map.getAnimals() .map {it.getPosition() }
                    .find { map.objectAt(it) == animal2 }
                animal2Position shouldNotBe null
                animal2Position shouldNotBe Vector2d(2, 2)
            }
        }
    }

    //test 3
    given("a full BouncyMap with no free positions") {
        val map = BouncyMap(2, 2)
        val animals = listOf(
            Animal(Vector2d(0, 0), MapDirection.NORTH),
            Animal(Vector2d(0, 1), MapDirection.SOUTH),
            Animal(Vector2d(1, 0), MapDirection.NORTH),
            Animal(Vector2d(1, 1), MapDirection.WEST)
        )
        animals.forEach { map.place(it) }

        `when`("placing a new animal") {
            val newAnimal = Animal(Vector2d(0, 0), MapDirection.NORTH)
            map.place(newAnimal)

            then("an existing animal should be removed, and the new one placed") {
                map.getAnimals().size shouldBe 4
                map.getAnimals() shouldContain newAnimal
            }
        }
    }
})
