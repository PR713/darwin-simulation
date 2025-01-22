import org.example.Vector2d

interface WorldElement {
    fun getPosition(): Vector2d

    override fun toString(): String
}