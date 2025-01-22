
import org.example.MapDirection
import org.example.MoveDirection
import org.example.Vector2d
import org.example.toUnitVector

class Animal(
    private var position: Vector2d = Vector2d(2, 2),
    private var orientation: MapDirection = MapDirection.NORTH
) : WorldElement {

    companion object {
        val upperRight: Vector2d = Vector2d(4, 4)
        val lowerLeft: Vector2d = Vector2d(0, 0)
    }

    override fun toString(): String {
        return when (this.orientation) {
            MapDirection.NORTH -> "^"
            MapDirection.EAST -> ">"
            MapDirection.SOUTH -> "v"
            MapDirection.WEST -> "<"
        }
    }

    fun isAt(position: Vector2d): Boolean {
        return this.position == position
    }

    override fun getPosition(): Vector2d {
        return this.position
    }

    fun getOrientation(): MapDirection {
        return this.orientation
    }

    fun move(validator: MoveValidator, direction: MoveDirection) {
        var newPosition = this.position
        when (direction) {
            MoveDirection.LEFT -> this.orientation = orientation.previous()
            MoveDirection.RIGHT -> this.orientation = orientation.next()
            MoveDirection.FORWARD -> newPosition = this.position + this.orientation.toUnitVector()
            MoveDirection.BACKWARD -> newPosition = this.position - this.orientation.toUnitVector()
        }
        if (validator.canMoveTo(newPosition)) {
            this.position = newPosition
        }
    }
}