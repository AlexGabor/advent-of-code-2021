@file:Suppress("PackageDirectoryMismatch", "PackageName")
package Day02

import readInput

fun part1(input: List<String>): Int {
    var distance = 0
    var depth = 0

    for (command in input.map { it.split(' ') }) {
        val direction = command[0]
        val value = command[1].toInt()

        when (direction) {
            "forward" -> distance += value
            "down" -> depth += value
            "up" -> depth = (depth - value).coerceAtLeast(0)
        }
    }

    return distance * depth
}

fun part2(input: List<String>): Int {
    var distance = 0
    var depth = 0
    var aim = 0

    for (command in input.map { it.split(' ') }) {
        val direction = command[0]
        val value = command[1].toInt()

        when (direction) {
            "forward" -> {
                distance += value
                depth += aim * value
                depth.coerceAtLeast(0)
            }
            "down" -> aim += value
            "up" -> aim -= value
        }
    }

    return distance * depth
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
