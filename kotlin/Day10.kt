@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day10

import readInput


fun part1(input: List<String>): Int {
    var score = 0
    for (line in input) {
        val stack = mutableListOf<Char>()
        for (char in line) {
            when {
                char.isOpening -> stack.add(char)
                char.isClosing -> {
                    when {
                        stack.isEmpty() -> {
                            score += char.score
                            break
                        }
                        stack.last() != char.matching -> {
                            score += char.score
                            break
                        }
                        stack.last() == char.matching -> stack.removeLast()
                    }
                }
            }
        }
    }
    return score
}

val Char.isOpening: Boolean
    get() = this == '(' || this == '[' || this == '{' || this == '<'

val Char.isClosing: Boolean
    get() = this == ')' || this == ']' || this == '}' || this == '>'

val Char.score: Int
    get() = when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        '(' -> 1
        '[' -> 2
        '{' -> 3
        '<' -> 4
        else -> 0
    }

val Char.matching: Char?
    get() = when (this) {
        ')' -> '('
        ']' -> '['
        '}' -> '{'
        '>' -> '<'
        else -> null
    }

fun part2(input: List<String>): Long {
    val scores = mutableListOf<Long>()
    line@ for (line in input) {
        val stack = mutableListOf<Char>()
        for (char in line) {
            when {
                char.isOpening -> stack.add(char)
                char.isClosing -> {
                    when {
                        stack.isEmpty() -> continue@line
                        stack.last() != char.matching -> continue@line
                        stack.last() == char.matching -> stack.removeLast()
                    }
                }
            }
        }
        scores += stack.foldRight(0) { c, acc -> acc * 5 + c.score }
    }
    return scores.sorted()[scores.size / 2]
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
