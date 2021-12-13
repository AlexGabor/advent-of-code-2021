@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day13

import readInput

data class Dot(val x: Int, val y: Int)

sealed class Fold {
    data class Y(val line: Int) : Fold()
    data class X(val line: Int) : Fold()
}

fun part1(input: List<String>): Int {
    val dots = mutableSetOf<Dot>()
    val folds = mutableListOf<Fold>()
    for (line in input) {
        when {
            line.isEmpty() -> {
            }
            line[0].isDigit() -> {
                val (x, y) = line.split(",").map { it.toInt() }
                dots.add(Dot(x, y))
            }
            line.startsWith("fold along") -> {
                val axis = line[11]
                val value = line.substring(13).toInt()
                folds.add(if (axis == 'x') Fold.X(value) else Fold.Y(value))
            }
        }
    }

    var result = foldedDots(dots, folds[0])

    return result.size
}

fun foldedDots(dots: Set<Dot>, fold: Fold): Set<Dot> = when (fold) {
    is Fold.X -> {
        dots.map { dot ->
            if (dot.x > fold.line) dot.copy(x = fold.line - (dot.x - fold.line)) else dot
        }.toSet()
    }
    is Fold.Y -> {
        dots.map { dot ->
            if (dot.y > fold.line) dot.copy(y = fold.line - (dot.y - fold.line)) else dot
        }.toSet()
    }
}


fun part2(input: List<String>): Int {
    val dots = mutableSetOf<Dot>()
    val folds = mutableListOf<Fold>()
    for (line in input) {
        when {
            line.isEmpty() -> {
            }
            line[0].isDigit() -> {
                val (x, y) = line.split(",").map { it.toInt() }
                dots.add(Dot(x, y))
            }
            line.startsWith("fold along") -> {
                val axis = line[11]
                val value = line.substring(13).toInt()
                folds.add(if (axis == 'x') Fold.X(value) else Fold.Y(value))
            }
        }
    }

    var result: Set<Dot> = dots
    for (fold in folds) {
        result = foldedDots(result, fold)
    }

    val display = Array<Array<String>>(result.maxOf { it.y } + 1) { Array(result.maxOf { it.x } + 1) { "." } }
    for (dot in result) {
        display[dot.y][dot.x] = "#"
    }
    for (line in display) {
        println(line.joinToString(separator = "") { it })
    }
    return result.size
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
//    check(part2(testInput) == 103)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
