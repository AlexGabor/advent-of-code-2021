@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day06

import readInput


fun part1(input: List<String>, days: Int = 80): Long {
    val school = Array(9) { 0L }
    input[0].split(",")
            .groupBy { it.toInt() }
            .map { it.key to it.value.size }
            .forEach { school[it.first] = it.second.toLong() }


    for (day in 1..days) {
        val zeroFish = school[0]
        for (index in 0..7) {
            school[index] = school[index + 1]
        }
        school[8] = zeroFish
        school[6] += zeroFish
    }
    return school.sum()
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput, 18) == 26L)
    check(part1(testInput, 256) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part1(input, 256))
}
