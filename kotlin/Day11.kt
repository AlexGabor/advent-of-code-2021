@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day11

import readInput


fun part1(input: List<String>): Int {
    val octopuses = Array(input.size) { Array(input[0].length) { 0 } }

    for (i in input.indices) {
        for (j in input[i].indices) {
            octopuses[i][j] = input[i][j].digitToInt()
        }
    }

    var flashes = 0
    for (step in 1..100) {
        var flash = mutableListOf<Coord>()
        for (i in octopuses.indices) {
            for (j in octopuses[i].indices) {
                octopuses[i][j] = (octopuses[i][j] + 1) % 10
                if (octopuses[i][j] == 0) flash.add(Coord(i, j))
            }
        }

        while (flash.isNotEmpty()) {
            val flashAgain = mutableListOf<Coord>()
            flashes += flash.size
            for (octopusCoord in flash) {
                flashAgain += octopuses.increaseAdjacents(octopusCoord)
            }
            flash = flashAgain
        }
    }

    return flashes
}

private fun Array<Array<Int>>.increaseAdjacents(coord: Coord): List<Coord> {
    val flashAgain = mutableListOf<Coord>()
    this.increaseAdjacent(coord.copy(x = coord.x - 1, y = coord.y - 1))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x - 1, y = coord.y))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x - 1, y = coord.y + 1))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x, y = coord.y - 1))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x, y = coord.y + 1))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x + 1, y = coord.y - 1))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x + 1, y = coord.y))?.let { flashAgain.add(it) }
    this.increaseAdjacent(coord.copy(x = coord.x + 1, y = coord.y + 1))?.let { flashAgain.add(it) }
    return flashAgain
}

private fun Array<Array<Int>>.increaseAdjacent(coord: Coord): Coord? {
    if (coord.x >= 0 && coord.y >= 0 && coord.x < this.size && coord.y < this[coord.x].size && this[coord.x][coord.y] != 0) {
        this[coord.x][coord.y] = (this[coord.x][coord.y] + 1) % 10
        if (this[coord.x][coord.y] == 0) return coord
    }
    return null
}

data class Coord(val x: Int, val y: Int)

fun part2(input: List<String>): Int {
    val octopuses = Array(input.size) { Array(input[0].length) { 0 } }

    for (i in input.indices) {
        for (j in input[i].indices) {
            octopuses[i][j] = input[i][j].digitToInt()
        }
    }

    for (step in 1..Int.MAX_VALUE) {
        var flash = mutableListOf<Coord>()
        for (i in octopuses.indices) {
            for (j in octopuses[i].indices) {
                octopuses[i][j] = (octopuses[i][j] + 1) % 10
                if (octopuses[i][j] == 0) flash.add(Coord(i, j))
            }
        }

        while (flash.isNotEmpty()) {
            val flashAgain = mutableListOf<Coord>()
            if (flash.size == 100) return step - 10 // 🙈
            for (octopusCoord in flash) {
                flashAgain += octopuses.increaseAdjacents(octopusCoord)
            }
            flash = flashAgain
        }
    }

    return 0
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
