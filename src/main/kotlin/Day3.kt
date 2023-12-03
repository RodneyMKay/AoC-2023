import java.nio.file.Paths
import kotlin.io.path.readLines

private sealed class Object {
    data class Number(val number: Int, val startX: Int, val endX: Int, val y: Int) : Object() {
        fun isNear(symbol: Symbol): Boolean {
            return symbol.x in (startX - 1 .. endX + 1) && symbol.y in (y - 1 .. y + 1)
        }
    }

    data class Symbol(val symbol: Char, val x: Int, val y: Int) : Object()
}

private fun readObjects(line: String, y: Int) = sequence {
    val chars = line.toCharArray()
    var position = 0

    while (position in chars.indices) {
        val c = chars[position]
        position++

        when {
            c == '.' -> {}
            c.isDigit() -> {
                val startX = position - 1

                val number = buildString {
                    append(c)

                    while (position in chars.indices && chars[position].isDigit()) {
                        append(chars[position])
                        position++
                    }
                }.toInt()

                val endX = position - 1

                yield(Object.Number(number, startX, endX, y))
            }
            else -> yield(Object.Symbol(c, position - 1, y))
        }
    }
}

fun main() {
    val lines = Paths.get("src", "main", "resources", "day3.txt").readLines()

    val objects = lines.map { line -> line.trim() }
        .withIndex()
        .flatMap { readObjects(it.value, it.index) }

    val symbols = objects.filterIsInstance<Object.Symbol>()
    val numbers = objects.filterIsInstance<Object.Number>()

    val task1 = numbers.filter { number -> symbols.any { number.isNear(it) } }
        .sumOf { it.number }

    println("Task 1: $task1")

    val task2 = symbols.filter { it.symbol == '*' }
        .map { symbol -> numbers.filter { it.isNear(symbol) } }
        .filter { it.size == 2 }
        .sumOf { it[0].number * it[1].number }

    println("Task 2: $task2")
}
