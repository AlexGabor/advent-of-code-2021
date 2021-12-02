package Day01

import readInput

fun part1(input: List<Int>): Int {
    return (1 until input.size).count { index ->
        input[index] > input[index - 1]
    }
}

fun part2(input: List<Int>): Int {
    return (1 until input.size - 2).count { index ->
        input[index] + input[index + 1] + input[index + 2] > input[index - 1] + input[index] + input[index + 1]
    }
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
