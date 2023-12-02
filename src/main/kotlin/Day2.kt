import java.nio.file.Paths
import kotlin.io.path.readLines

private data class Game(
    val id: Int,
    val cubeSets: List<Map<String, Int>>
)

private fun Game.isPossible(): Boolean {
    return cubeSets.all {
        it.getOrDefault("red", 0) <= 12 &&
                it.getOrDefault("green", 0) <= 13 &&
                it.getOrDefault("blue", 0) <= 14
    }
}

private fun Game.getPower(): Long {
    return cubeSets.maxOf { it.getOrDefault("red", 0) }.toLong() *
            cubeSets.maxOf { it.getOrDefault("green", 0) }.toLong() *
            cubeSets.maxOf { it.getOrDefault("blue", 0) }.toLong()
}

fun main() {
    val lines = Paths.get("src", "main", "resources", "day2.txt").readLines()

    val games = lines.map { line -> readGame(line) }

    val task1 = games.filter { it.isPossible() }
        .sumOf { it.id }

    println("Task 1: $task1")

    val task2 = games.sumOf { it.getPower() }

    println("Task 2: $task2")
}

private fun readGame(line: String): Game {
    val id = line.substringBefore(":")
        .removePrefix("Game ")
        .toInt()

    val cubeSets = line.substringAfter(":")
        .split(';')
        .map { move ->
            move.split(',')
                .map { it.trim().split(" ") }
                .associate { it[1] to it.first().toInt() }
        }

    return Game(id, cubeSets)
}
