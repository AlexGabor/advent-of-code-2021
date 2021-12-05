@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day05

import readInput
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max


data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point) {
    //    val isPoint: Boolean get() = start == end
    val isHorizontal: Boolean get() = start.y == end.y
    val isVertical: Boolean get() = start.x == end.x
    val diagonal: Boolean get() = !isHorizontal && !isVertical
//    val length: Int get() = sqrt((start.x - end.x) * (start.x - end.x) + (start.y - end.y) * (start.y - end.y)).toInt()

    fun overlapsWith(other: Line): Line? {
        return when {
            this.isHorizontal == other.isHorizontal && this.start.y == other.start.y -> {
                if ((min(this.end.x, other.end.x) - max(this.start.x, other.start.x) + 1) > 0) {
                    Line(Point(max(this.start.x, other.start.x), start.y), Point(min(this.end.x, other.end.x), end.y))
                } else null
            }
            this.isVertical == other.isVertical && this.start.x == other.start.x -> {
                if ((min(this.end.y, other.end.y) - max(this.start.y, other.start.y) + 1) > 0) {
                    Line(Point(start.x, max(this.start.y, other.start.y)), Point(end.x, min(this.end.y, other.end.y)))
                } else null
            }
            this.isVertical && other.isHorizontal &&
                    other.start.x <= this.start.x && this.start.x <= other.end.x &&
                    this.start.y <= other.start.y && other.start.y <= this.end.y -> {
                Line(Point(this.start.x, other.start.y), Point(this.start.x, other.start.y))
            }
            this.isHorizontal && other.isVertical &&
                    this.start.x <= other.start.x && other.start.x <= this.end.x &&
                    other.start.y <= this.start.y && this.start.y <= other.end.y -> {
                Line(Point(other.start.x, this.start.y), Point(other.start.x, this.start.y))
            }
            else -> null
        }
    }
}

fun part1(input: List<String>): Int {
    val lines = input.map { line ->
        val lineParts = line.split(" -> ", ",").map { it.toInt() }
        Line(Point(lineParts[0], lineParts[1]), Point(lineParts[2], lineParts[3]))
    }.filter {
        it.isHorizontal || it.isVertical
    }.map {
        if (it.start.x > it.end.x || it.start.y > it.end.y) Line(it.end, it.start)
        else it
    }

    val overlapPoints = mutableListOf<Point>()
    for (i in lines.indices) {
        for (j in i + 1 until lines.size) {
            lines[i].overlapsWith(lines[j])?.let { overlapLine ->
                overlapPoints.addAll(overlapLine.toPoints())
            }
        }
    }
    return overlapPoints.distinct().size
}

private fun Line.toPoints(): List<Point> {
    return if (isVertical) (start.y..end.y).map { y -> Point(start.x, y) }
    else (start.x..end.x).map { x -> Point(x, start.y) }
}

fun part2(input: List<String>): Int {

    return 0
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
//    check(part2(testInput) == 1924)

    val input = readInput("Day05")
    println(part1(input))
//    println(part2(input))
}
