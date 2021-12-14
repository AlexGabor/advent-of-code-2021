@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day14

import readInput

fun part1(input: List<String>): Int {
    var template = mutableListOf<Char>()
    for (char in input[0]) {
        template.add(char)
    }

    val pairs = mutableMapOf<String, Char>()
    for (line in input.subList(2, input.size)) {
        val (pair, insert) = line.split(" -> ")
        pairs[pair] = insert.first()
    }

    for (step in 1..10) {
        val newPolymer = mutableListOf<Char>()
        template.windowed(2).forEach { chars ->
            val pair = chars.joinToString(separator = "")
            newPolymer.add(chars[0])
            pairs[pair]?.let { insert ->
                newPolymer.add(insert)
            }
        }
        newPolymer.add(template.last())
        template = newPolymer
    }
    val frequency = template.groupBy { it }.map { it.value.size }.sorted()
    return frequency.last() - frequency.first()
}


fun part2(input: List<String>): Long {
    val template = mutableListOf<Char>()
    for (char in input[0]) {
        template.add(char)
    }

    val pairs = mutableMapOf<String, Char>()
    for (line in input.subList(2, input.size)) {
        val (pair, insert) = line.split(" -> ")
        pairs[pair] = insert.first()
    }


    val count = template.windowed(2).fold(mapOf(template.last() to 1L)) { acc, list ->
        mergeMaps(acc, grow(list.joinToString(separator = ""), 40, pairs))
    }
    return count.maxOf { it.value } - count.minOf { it.value }
}

val cache = mutableMapOf<Pair<String, Int>, Map<Char, Long>>()

fun grow(polymer: String, step: Int, pairs: Map<String, Char>): Map<Char, Long> {
    if (step == 0) return mapOf(polymer[0] to 1L)

    cache[polymer to step]?.let { return it }

    pairs[polymer]?.let { insert ->
        val left = grow(listOf(polymer[0], insert).joinToString(separator = ""), step - 1, pairs)
        val right = grow(listOf(insert, polymer[1]).joinToString(separator = ""), step - 1, pairs)
        return mergeMaps(left, right).also { cache[polymer to step] = it }
    }
    return mapOf(polymer[0] to 1L)
}

fun mergeMaps(left: Map<Char, Long>, right: Map<Char, Long>): MutableMap<Char, Long> {
    val count = mutableMapOf<Char, Long>()
    left.forEach { (key, value) -> count[key] = value }
    right.forEach { (key, value) -> count[key] = count[key]?.plus(value) ?: value }
    return count
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
//    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
