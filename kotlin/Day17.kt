@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day17

import readInput
import kotlin.math.abs
import kotlin.math.sqrt


fun readNumbers(input: List<String>): List<Int> {
    val numbers = mutableListOf<Int>()
    var index = 0
    while (index < input[0].length) {
        if (input[0][index] == '-' || input[0][index].isDigit()) {
            var digits = ""
            while (index < input[0].length && (input[0][index] == '-' || input[0][index].isDigit())) {
                digits += input[0][index]
                index++
            }
            numbers.add(digits.toInt())
        }
        index++
    }
    return numbers
}

fun part1(input: List<String>): Int {
    val (xStart, xEnd, yEnd, yStart) = readNumbers(input)

    val y = abs(yEnd) - 1
    return y * (y + 1) / 2
}

fun part2(input: List<String>): Int {
    val (xStart, xEnd, yEnd, yStart) = readNumbers(input)

    var count = 0
    for (x in sqrt(2.0 * xStart).toInt()..xEnd) {
        for (y in yEnd until abs(yEnd)) {
            var posX = 0
            var posY = 0
            var stepX = x
            var stepY = y
            while (posY >= yEnd && posX <= xEnd) {
                posX += stepX
                posY += stepY
                stepX = (stepX - 1).coerceAtLeast(0)
                stepY--
                if (posX in xStart..xEnd && posY in yEnd..yStart) {
                    count++
                    break
                }
            }
        }
    }
    return count
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 45)
    check(part2(testInput) == 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
