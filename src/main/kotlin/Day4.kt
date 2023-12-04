import java.nio.file.Paths
import kotlin.io.path.readLines

private data class Card(
    val winningNumbers: Set<Int>,
    val myNumbers: List<Int>,
    var count: Int = 1
)

private fun readCard(line: String): Card {
    val numbers = line.substringAfter(":")

    val winningNumbers = numbers.substringBefore("|")
        .split("\\s+".toRegex())
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toHashSet()

    val myNumbers = numbers.substringAfter("|")
        .split("\\s+".toRegex())
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    return Card(
        winningNumbers,
        myNumbers
    )
}

fun main() {
    val lines = Paths.get("src", "main", "resources", "day4.txt").readLines()

    val cards = lines.map { readCard(it) }

    val task1 = cards.map { card -> card.myNumbers.count { card.winningNumbers.contains(it) } }
        .filter { it > 0 }
        .sumOf { 1 shl (it - 1) }

    println("Task 1: $task1")

    for ((index, card) in cards.withIndex()) {
        val number = card.myNumbers.count { card.winningNumbers.contains(it) }

        cards.subList(index + 1, index + 1 + number)
            .forEach { it.count += card.count }
    }

    val task2 = cards.sumOf { it.count }

    println("Task 2: $task2")
}
