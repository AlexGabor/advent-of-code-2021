@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day08

import readInput


fun part1(input: List<String>): Int {
    var count = 0
    for (line in input) {
        val (inputDigits, outputDigits) = line.split('|').map { it.split(" ") }

        count += outputDigits.count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    }
    return count
}

val segmentsToDigit = mutableMapOf<String, Int>(
        "cf" to 1,
        "acf" to 7,
        "bcdf" to 4,
        "acdeg" to 2,
        "acdfg" to 3,
        "abdfg" to 5,
        "abcefg" to 0,
        "abdefg" to 6,
        "abcdfg" to 9,
        "abcdefg" to 8
)

fun part2(input: List<String>): Int {
    var count = 0
    for (line in input) {
        val map = mutableMapOf<Char, Char>()
        val splitOutput = line.split('|').map { it.split(" ").filter { it.isNotEmpty() } }
        val inputDigits = splitOutput[0].map { it.toSet() }.groupBy { it.size }
        val outputDigits = splitOutput[1]


        val interesct5 = inputDigits[5]!!.reduce { acc, set -> acc.intersect(set) }
        val interesct6 = inputDigits[6]!!.reduce { acc, set -> acc.intersect(set) }
        map[(inputDigits[3]!![0] - inputDigits[2]!![0]).first()] = 'a'
        map[(inputDigits[4]!![0] - inputDigits[2]!![0] - interesct5).first()] = 'b'
        map[(inputDigits[2]!![0] - interesct6).first()] = 'c'
        map[(interesct5 - interesct6).first()] = 'd'
        map[(inputDigits[7]!![0] - interesct6 - interesct5 - inputDigits[2]!![0]).first()] = 'e'
        map[(inputDigits[2]!![0] - (inputDigits[2]!![0] - interesct6)).first()] = 'f'
        map[(interesct5 - (inputDigits[3]!![0] - inputDigits[2]!![0]) - (interesct5 - interesct6)).first()] = 'g'

        val number = outputDigits.map { digit ->
            digit.map { map[it]!! }.sorted().joinToString(separator = "")
        }.map {
            segmentsToDigit[it]!!
        }.joinToString(separator = "") { it.toString() }.toInt()

        count += number
    }
    return count
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
