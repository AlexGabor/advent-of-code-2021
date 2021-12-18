@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day15

import readInput

fun part1(input: List<String>): Int {
    val sum = Array(input.size) { Array(input[0].length) { 0 } }
    val cave = Array(input.size) { Array(input[0].length) { 0 } }

    for ((i, line) in input.withIndex()) {
        for ((j, char) in line.withIndex()) {
            cave[i][j] = input[i][j].digitToInt()
        }
    }

    findPath(cave, 0, 0, sum)

    return sum.last().last() - sum.first().first()
}

fun findPath(cave: Array<Array<Int>>, i: Int, j: Int, sums: Array<Array<Int>>) {
    if (!(i >= 0 && i < cave.size && j >= 0 && j < cave[i].size)) return

    val neighbours = cave.findNeighbours(i, j)
    for (neighbour in neighbours) {
        val newSum = sums[i][j] + cave[neighbour.first][neighbour.second]
        if (sums[neighbour.first][neighbour.second] == 0 || newSum < sums[i][j]) {
            sums[neighbour.first][neighbour.second] = newSum
        }
    }
    val next = neighbours.minByOrNull { sums[it.first][it.second] }!!
    findPath(cave, next.first, next.second, sums)
}

fun Array<Array<Int>>.findNeighbours(i: Int, j: Int): List<Pair<Int, Int>> = buildList {
    if (j > 0) add(i to j - 1)
    if (j < this@findNeighbours[0].size - 1) add(i to j + 1)
    if (i > 0) add(i - 1 to j)
    if (i < this@findNeighbours.size - 1) add(i + 1 to j)
}


fun part2(input: List<String>): Long {
    return 0
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
//    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day15")
    println(part1(input))
//    println(part2(input))
}
