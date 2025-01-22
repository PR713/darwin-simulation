package org.example

data class Vector2d(val x: Int, val y: Int){
    override fun toString(): String = "($x,$y)"

    operator fun plus(other: Vector2d): Vector2d = Vector2d(x + other.x, y + other.y)

    operator fun minus(other: Vector2d): Vector2d = Vector2d(x - other.x, y - other.y)

    operator fun unaryMinus(): Vector2d = Vector2d(-x, -y)

    infix fun precedes(other: Vector2d): Boolean = x <= other.x && y <= other.y

    infix fun follows(other: Vector2d): Boolean = x >= other.x && y >= other.y

    fun upperRight(other: Vector2d): Vector2d = Vector2d(maxOf(x, other.x), maxOf(y, other.y))

    fun lowerLeft(other: Vector2d): Vector2d = Vector2d(minOf(x, other.x), minOf(y, other.y))

}

fun MapDirection.toUnitVector(): Vector2d {
    return when (this) {
        MapDirection.NORTH -> Vector2d(0, 1)
        MapDirection.EAST -> Vector2d(1, 0)
        MapDirection.SOUTH -> Vector2d(0, -1)
        MapDirection.WEST -> Vector2d(-1, 0)
    }
}