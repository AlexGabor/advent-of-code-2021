@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day12

import readInput

class Graph {
    val edges = mutableMapOf<String, MutableList<String>>()
}

fun part1(input: List<String>): Int {
    val graph = Graph()
    for (edge in input) {
        val (start, end) = edge.split("-")
        if (!graph.edges.containsKey(start)) graph.edges[start] = mutableListOf()
        if (!graph.edges.containsKey(end)) graph.edges[end] = mutableListOf()
        if (start != "end" && end != "start") graph.edges[start]?.add(end)
        if (start != "start" && end != "end") graph.edges[end]?.add(start)
    }

    val path = emptyList<String>()
    return graph.searchPaths(path, "start")
}

fun Graph.searchPaths(path: List<String>, start: String): Int {
    val startNode = this.edges[start] ?: return 0
    if (start[0].isLowerCase() && path.contains(start)) return 0
    if (start == "end") return 1
    val newPath = path + listOf(start)
    return startNode.fold(0) { acc, end -> acc + searchPaths(newPath, end) }
}


fun Graph.searchPathsPart2(path: List<String>, start: String, visitedTwice: Boolean = false): Int {
    val startNode = this.edges[start] ?: return 0
    if (start[0].isLowerCase() && path.contains(start) && visitedTwice) return 0
    val twice = visitedTwice || (start[0].isLowerCase() && path.contains(start))
    val newPath = path + listOf(start)
    if (start == "end") return 1
    return startNode.fold(0) { acc, end -> acc + searchPathsPart2(newPath, end, twice) }
}


fun part2(input: List<String>): Int {
    val graph = Graph()
    for (edge in input) {
        val (start, end) = edge.split("-")
        if (!graph.edges.containsKey(start)) graph.edges[start] = mutableListOf()
        if (!graph.edges.containsKey(end)) graph.edges[end] = mutableListOf()
        if (start != "end" && end != "start") graph.edges[start]?.add(end)
        if (start != "start" && end != "end") graph.edges[end]?.add(start)
    }

    val path = emptyList<String>()
    return graph.searchPathsPart2(path, "start")
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 19)
    check(part2(testInput) == 103)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
