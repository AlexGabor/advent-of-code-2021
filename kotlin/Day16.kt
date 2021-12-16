@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day16

import readInput

var versionSum = 0

fun part1(input: List<String>): Long {

    for (line in input) {
        versionSum = 0
        val bits = line.toBits()
        val (value, _) = decodePackage(bits)
        println(value)
        return value
    }
    return 0
}

fun decodePackage(bits: String): Pair<Long, Int> {
    val version = bits.take(3).toInt(2)
    versionSum += version
    val type = bits.substring(3..5).toInt(2)
    when (type) {
        4 -> {
            val (literal, lengthConsumed) = bits.substring(6).readLiteral()
            return literal to 6 + lengthConsumed
        }
        0 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return values.sum() to 6 + consumed
        }
        1 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return values.reduce { acc, l -> acc * l } to 6 + consumed
        }
        2 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return values.minOrNull()!! to 6 + consumed
        }
        3 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return values.maxOrNull()!! to 6 + consumed
        }
        5 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return (if (values[0] > values[1]) 1L else 0L) to 6 + consumed
        }
        6 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return (if (values[0] < values[1]) 1L else 0L) to 6 + consumed
        }
        7 -> {
            val (values, consumed) = bits.substring(6).decodeOperator()
            return (if (values[0] == values[1]) 1L else 0L) to 6 + consumed
        }
        else -> {
            throw IllegalStateException()
        }
    }
}

private fun String.decodeOperator(): Pair<List<Long>, Int> {
    when (this[0]) {
        '0' -> {
            val length = this.substring(1..15).toInt(2)
            var lengthConsumed = 0
            val values = mutableListOf<Long>()
            do {
                val (value, consumed) = decodePackage(this.substring(16 + lengthConsumed))
                values.add(value)
                lengthConsumed += consumed
            } while (lengthConsumed < length)
            return values to lengthConsumed + 16
        }
        '1' -> {
            var subPackets = this.substring(1..11).toInt(2)
            var lengthConsumed = 0
            val values = mutableListOf<Long>()
            while (subPackets > 0) {
                val (value, consumed) = decodePackage(this.substring(12 + lengthConsumed))
                values.add(value)
                lengthConsumed += consumed
                subPackets -= 1
            }
            return values to lengthConsumed + 12
        }
    }
    return emptyList<Long>() to 0
}

private fun String.readLiteral(): Pair<Long, Int> {
    var chunk = this.substring(0..4)
    var step = 0
    var string = ""
    while (true) {
        string += chunk.takeLast(4)
        step += 1
        if (chunk[0] == '0') break
        chunk = this.substring(step * 5, (step + 1) * 5)
    }
    return string.toLong(2) to step * 5 // literal to length consumed
}


private fun String.toBits(): String {
    return this.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString(separator = "")
}

fun part2(input: List<String>): Long {
    return 0
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 0L)
//    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day16")
    println(part1(input))
//    println(part2(input))
}
