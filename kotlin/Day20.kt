@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day20

import readInput
import kotlin.math.abs
import kotlin.math.sqrt

data class Coord(val x: Int, val y: Int)

class Image(val size: Coord, val background: Int) {
    val bits = mutableMapOf<Coord, Int>()

    val lightCount: Int get() = bits.count { (_, value) -> value == 1 }

    fun print() {
        for (i in 0 until size.x) {
            for (j in 0 until size.y) {
                print(if (bits[Coord(i,j)] == 0) "." else "#")
            }
            println()
        }
    }
}

fun part1(input: List<String>, times: Int): Int {
    val algorithm = input[0].map { if (it == '.') 0 else 1 }
    val image = Image(Coord(input.size - 2, input[2].length), 0)
    for (index in 2 until input.size) {
        input[index].map { if (it == '.') 0 else 1 }.forEachIndexed { j, value ->
            image.bits[Coord(index - 2, j)] = value
        }
    }

    var currentImage = image
    repeat(times) {
        val newBackground = if (currentImage.background == 0) algorithm.first() else algorithm.last()
        val newImage = Image(currentImage.size.copy(x = currentImage.size.x + 2, y = currentImage.size.y + 2), newBackground)
        for (i in 0 until newImage.size.x) {
            for (j in 0 until newImage.size.y) {
                newImage.bits[Coord(i, j)] = algorithm[currentImage.getIndexOf(i - 1, j - 1)]
            }
        }
//        newImage.print()
//        println()
        currentImage = newImage
    }

    currentImage.print()
    println()
    return currentImage.lightCount
}

private fun Image.getIndexOf(i: Int, j: Int): Int {
    var index = 0
    index += (bits[Coord(i - 1, j - 1)] ?: background) shl 8
    index += (bits[Coord(i - 1, j)] ?: background) shl 7
    index += (bits[Coord(i - 1, j + 1)] ?: background) shl 6
    index += (bits[Coord(i, j - 1)] ?: background) shl 5
    index += (bits[Coord(i, j)] ?: background) shl 4
    index += (bits[Coord(i, j + 1)] ?: background) shl 3
    index += (bits[Coord(i + 1, j - 1)] ?: background) shl 2
    index += (bits[Coord(i + 1, j)] ?: background) shl 1
    index += bits[Coord(i + 1, j + 1)] ?: background
    return index
}

fun part2(input: List<String>): Int {
    return 0
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput, 2) == 35)
    check(part1(testInput, 50) == 3351)

    val input = readInput("Day20")
    println(part1(input, 2))
    println(part1(input, 50))
}
