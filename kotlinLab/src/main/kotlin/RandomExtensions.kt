package agh.ics.oop.extensions

import org.example.Vector2d

fun Map<Vector2d, *>.randomPosition(): Vector2d? {
    return this.keys.randomOrNull()
}

fun Map<Vector2d, *>.randomFreePosition(mapSize: Vector2d): Vector2d? {
    val allPositions = (0 until mapSize.x).flatMap { x ->
        (0 until mapSize.y).map { y -> Vector2d(x, y) }
    }

    val occupiedPositions = this.keys
    val freePositions = allPositions.filter { it !in occupiedPositions }

    return freePositions.randomOrNull()
}
