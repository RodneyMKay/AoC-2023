import java.nio.file.Paths
import kotlin.io.path.readLines

private val numbers = mapOf(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
)

fun main() {
    val lines = Paths.get("src", "main", "resources", "day1.txt").readLines()

    val valuesTask1 = lines.map { line ->
        val firstDigit = line.first { it.isDigit() }
        val lastDigit = line.last { it.isDigit() }
        String(charArrayOf(firstDigit, lastDigit)).toInt()
    }

    println("Task 1: ${valuesTask1.sum()}")

    val valuesTask2 = lines.map { line ->
        val firstIndex = line.indexOfFirst { it.isDigit() }
        val lastIndex = line.indexOfLast { it.isDigit() }

        val firstString = if (firstIndex != -1) line.substring(0, firstIndex) else line
        val lastString = if (lastIndex != -1) line.substring(lastIndex) else line

        val firstWordIndex = firstString.indexOfAny(numbers.keys)
        val lastWordIndex = lastString.lastIndexOfAny(numbers.keys)

        val firstChar = when {
            firstWordIndex == -1 -> line[firstIndex]
            else -> numbers[numbers.keys.find { firstString.startsWith(it, startIndex = firstWordIndex) }]!!
        }

        val lastChar = when {
            lastWordIndex == -1 -> line[lastIndex]
            else -> numbers[numbers.keys.find { lastString.startsWith(it, startIndex = lastWordIndex) }]!!
        }

        String(charArrayOf(firstChar, lastChar)).toInt()
    }

    println("Task 2: ${valuesTask2.sum()}")
}
