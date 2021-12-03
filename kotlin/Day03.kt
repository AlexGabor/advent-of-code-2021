@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day03

import readInput

fun part1(input: List<String>): Int {
    val bitFrequency = IntArray(input[0].length) { 0 }
    for (number in input) {
        number.forEachIndexed { index, c ->
            if (c == '1') {
                bitFrequency[index]++
            }
        }
    }

    var gamma = 0
    bitFrequency.forEachIndexed { index, frequency ->
        if (frequency > input.size / 2) {
            gamma = gamma or (1 shl (bitFrequency.size - 1 - index))
        }
    }

    var epsilon = 0
    for (index in bitFrequency.indices) {
        epsilon = epsilon or ((gamma and (1 shl index)) xor (1 shl index))
    }

    println("gamma: $gamma, epsilon: $epsilon")
    return gamma * epsilon
}

fun part2(input: List<String>): Int {
    var oxygenRating = input
    var co2Rating = input
    var position = 0
    while (oxygenRating.size > 1) {
        val zeroNumbers = mutableListOf<String>()
        val oneNumbers = mutableListOf<String>()
        oxygenRating.forEach { number ->
            if (number[position] == '0') zeroNumbers.add(number)
            else oneNumbers.add(number)
        }
        oxygenRating = when {
            oneNumbers.size >= zeroNumbers.size -> oneNumbers
            else -> zeroNumbers
        }
        position++
    }

    position = 0
    while (co2Rating.size > 1) {
        val zeroNumbers = mutableListOf<String>()
        val oneNumbers = mutableListOf<String>()
        co2Rating.forEach { number ->
            if (number[position] == '0') zeroNumbers.add(number)
            else oneNumbers.add(number)
        }
        co2Rating = when {
            zeroNumbers.size <= oneNumbers.size -> zeroNumbers
            else -> oneNumbers
        }
        position++
    }

    val oxygen = oxygenRating[0].bitsToInt()
    val co2 = co2Rating[0].bitsToInt()

    println("oxygen: $oxygen, co2: $co2")
    return oxygen * co2
}

fun String.bitsToInt(): Int {
    var number = 0
    this.forEachIndexed { index, c ->
        if (c == '1') {
            number = number or (1 shl this.length - 1 - index)
        }
    }
    return number
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
