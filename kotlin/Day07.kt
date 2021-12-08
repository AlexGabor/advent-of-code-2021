@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day07

import readInput


fun part1(input: List<String>): Int {
    val crabs = input[0].split(",").map { it.toInt() }.sorted()

    val sums = Array(crabs.size) { 0 }
    sums[0] = crabs.sum()
    var min = sums[0]
    for (index in 1 until crabs.size) {
        val diff = crabs[index] - crabs[index - 1]
        sums[index] = sums[index - 1] + diff * index - diff * (crabs.size - index)
        if (sums[index] < min) min = sums[index]
    }

    return min
}

fun part2(input: List<String>): Int {
    val crabsInput = input[0].split(",").map { it.toInt() }.sorted()
    val crabs = mutableListOf(crabsInput[0])
    for (index in 1 until crabsInput.size) {
        crabs.add(sumOfN(crabsInput[index] - crabsInput[0]))
    }

    val sums = Array(crabs.size) { 0 }
    sums[0] = crabs.sum()
    var min = sums[0]
    for (index in 1 until crabs.size) {
        val diff = crabs[index] - crabs[index - 1]
        sums[index] = sums[index - 1] + diff * index - diff * (crabs.size - index)
        if (sums[index] < min) min = sums[index]
    }

    return min
}

fun sumOfN(n: Int) = n*(n+1)/2

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 206)

    val input = readInput("Day07")
    println(part1(input))
//    println(part1(input, 256))
}
