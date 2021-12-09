@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day09

import readInput


fun part1(input: List<String>): Int {
    var risk = 0
    for (i in input.indices) {
        for (j in input[i].indices) {
            if ((i == 0 || input[i][j] < input[i - 1][j]) &&
                    (i == input.size - 1 || input[i][j] < input[i + 1][j]) &&
                    (j == 0 || input[i][j] < input[i][j - 1]) &&
                    (j == input[i].length - 1 || input[i][j] < input[i][j + 1])) {
                risk += 1 + input[i][j].digitToInt()
            }
        }
    }
    return risk
}

fun part2(input: List<String>): Int {
    val cave = Array(input.size) { Array(input[0].length) { 0 } }

    for (i in input.indices) {
        for (j in input[i].indices) {
            cave[i][j] = input[i][j].digitToInt()
        }
    }

    val basins = mutableListOf<Int>()
    for (i in cave.indices) {
        for (j in cave[i].indices) {
            if (cave.isLowest(i, j)) {
                basins.add(cave.fillBasin(i, j))
            }
        }
    }

    return basins.sorted().takeLast(3).reduce { acc, i -> acc * i }
}

private fun Array<Array<Int>>.fillBasin(i: Int, j: Int): Int {
    if (this.isOutOfBounds(i, j) || this[i][j] == 9) return 0
    this[i][j] = 9
    return 1 + fillBasin(i + 1, j) + fillBasin(i - 1, j) + fillBasin(i, j + 1) + fillBasin(i, j - 1)
}

private fun Array<Array<Int>>.isOutOfBounds(i: Int, j: Int): Boolean {
    return (i == -1 || i == this.size || j == -1 || j == this[i].size)
}

private fun Array<Array<Int>>.isLowest(i: Int, j: Int): Boolean = (i == 0 || this[i][j] < this[i - 1][j]) &&
        (i == this.size - 1 || this[i][j] < this[i + 1][j]) &&
        (j == 0 || this[i][j] < this[i][j - 1]) &&
        (j == this[i].size - 1 || this[i][j] < this[i][j + 1])

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
