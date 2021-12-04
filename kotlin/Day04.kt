@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day04

import readInput

data class Location(
        val board: Int,
        val row: Int,
        val col: Int,
)

class Board(
        val numbers: MutableList<Array<BoardNumber>> = mutableListOf()
) {
    private var rowMarks = Array(5) { 0 }
    private var colMarks = Array(5) { 0 }

    fun markNumber(row: Int, col: Int) {
        rowMarks[row]++
        colMarks[col]++
        numbers[row][col] = numbers[row][col].copy(isMarked = true)
    }

    val isBingo: Boolean
        get() = rowMarks.contains(5) || colMarks.contains(5)
}

data class BoardNumber(
        val value: Int,
        val isMarked: Boolean = false,
)

val numberLocations = Array<MutableList<Location>>(100) { mutableListOf() }
val boards = mutableListOf<Board>()

fun part1(input: List<String>): Int {
    val numbers = input[0].split(',').map { it.toInt() }

    var inputIndex = 2
    var boardIndex = 0
    while (inputIndex < input.size) {
        boards.add(Board())
        for (row in 0 until 5) {
            val numbersInRow = input[inputIndex + row].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            for ((col, number) in numbersInRow.withIndex()) {
                numberLocations[number].add(Location(boardIndex, row, col))
            }
            boards[boardIndex].numbers.add(numbersInRow.map { BoardNumber(it) }.toTypedArray())
        }
        boardIndex++
        inputIndex += 6
    }

    for (draw in numbers) {
        for (location in numberLocations[draw]) {
            boards[location.board].markNumber(location.row, location.col)
            if (boards[location.board].isBingo) {
                return boards[location.board].getUnmarkedSum() * draw
            }
        }
    }
    return 0
}

private fun Board.getUnmarkedSum(): Int {
    var sum = 0
    for (row in numbers) {
        for (number in row) {
            if (!number.isMarked) {
               sum += number.value
            }
        }
    }
    return sum
}

fun part2(input: List<String>): Int {
    val numbers = input[0].split(',').map { it.toInt() }

    var inputIndex = 2
    var boardIndex = 0
    while (inputIndex < input.size) {
        boards.add(Board())
        for (row in 0 until 5) {
            val numbersInRow = input[inputIndex + row].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            for ((col, number) in numbersInRow.withIndex()) {
                numberLocations[number].add(Location(boardIndex, row, col))
            }
            boards[boardIndex].numbers.add(numbersInRow.map { BoardNumber(it) }.toTypedArray())
        }
        boardIndex++
        inputIndex += 6
    }

    var bingos = 0
    for (draw in numbers) {
        for (location in numberLocations[draw]) {
            val wasBingo = boards[location.board].isBingo
            if (!wasBingo) {
                boards[location.board].markNumber(location.row, location.col)
                if (boards[location.board].isBingo) {
                    bingos++
                    if (bingos == boards.size) {
                        return boards[location.board].getUnmarkedSum() * draw
                    }
                }
            }
        }
    }
    return 0
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
//    check(part1(testInput) == 4512)
//    check(part2(testInput) == 1924)

    val input = readInput("Day04")
//    println(part1(input))
//    println(part2(input))
}
