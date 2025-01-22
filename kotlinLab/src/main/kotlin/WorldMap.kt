
import org.example.MoveDirection
import org.example.Vector2d
import java.util.UUID

interface WorldMap : MoveValidator {

    /**
     * Umieszcza zwierzę na mapie.
     * lub przemieszcza zwierzę na mapie
     * @param animal Zwierzę, które ma zostać umieszczone/przemieszczone.
     */

    fun place(animal: Animal)


    /**
     * Zwraca true, jeśli dana pozycja jest zajęta.
     * @param position Pozycja do sprawdzenia.
     * @return true jeśli pozycja jest zajęta.
     */
    fun isOccupied(position: Vector2d): Boolean

    /**
     * Zwraca zwierzę znajdujące się na danej pozycji.
     * @param position Pozycja zwierzęcia.
     * @return Zwierzę lub null, jeśli brak zwierzęcia na danej pozycji.
     */
    fun objectAt(position: Vector2d): WorldElement?

    /**
     * Zwraca kolekcję wszystkich elementów na mapie.
     * @return Kolekcja elementów na mapie.
     */
    fun getAnimals(): List<WorldElement>

//    /**
//     * Zwraca aktualne granice mapy.
//     * @return Granice mapy.
//     */
//    fun getCurrentBounds(): Boundary

//    /**
//     * Zwraca identyfikator mapy.
//     * @return UUID mapy.
//     */
//    fun getId(): UUID
    //"Na potrzeby tej laborki nie będziemy potrzebowali żadnych elementów UI"
}