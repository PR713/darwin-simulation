package org.example

enum class MapDirection(private val unitVector: Vector2d) {
    NORTH(Vector2d(0, 1)),
    SOUTH(Vector2d(0, -1)),
    WEST(Vector2d(-1, 0)),
    EAST(Vector2d(1, 0));

    override fun toString(): String = when (this) {
        NORTH -> "Północ"
        SOUTH -> "Południe"
        WEST -> "Zachód"
        EAST -> "Wschód"
    }

    fun next(): MapDirection = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun previous(): MapDirection = when (this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }

}