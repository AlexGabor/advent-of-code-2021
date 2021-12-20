@file:Suppress("PackageDirectoryMismatch", "PackageName")

package Day18

import readInput
import java.lang.Long.max

sealed class Node {
    operator fun plus(other: Node): Node.Pair = Node.Pair(left = this, right = other, parent = null).also { root ->
        if (this is Pair) this.parent = root
        if (other is Pair) other.parent = root
    }

    class Pair(var left: Node? = null, var right: Node? = null, var parent: Pair? = null) : Node() {
        override fun toString(): String = "[$left,$right]"
    }

    class Number(val number: Int) : Node() {
        override fun toString(): String = number.toString()
    }
}

fun Node.Pair.fillBranch(node: Node) {
    if (left == null) left = node
    else right = node
}

fun readTree(line: String): Node.Pair {
    var current: Node.Pair? = null
    var index = 0
    while (index < line.length) {
        when {
            line[index] == '[' -> {
                val newNode = Node.Pair(parent = current)
                current?.fillBranch(newNode)
                current = newNode
            }
            line[index].isDigit() -> {
                val newNode = Node.Number(line[index].digitToInt())
                current?.fillBranch(newNode)
            }
            line[index] == ']' && index != line.length - 1 -> current = current!!.parent
        }
        index++
    }
    return current!!
}

fun part1(input: List<String>): Long {
    var currentTree = readTree(input[0])
    println(currentTree)

    var index = 1
    while (index < input.size) {
        val newTree = readTree(input[index])
        println("  $currentTree + $newTree")
        currentTree += newTree
        println("= $currentTree")
        currentTree.explode()
        println(currentTree)
        println()
        index++
    }
    return currentTree.magnitude()
}

private fun Node.magnitude(): Long = when (this) {
    is Node.Number -> this.number.toLong()
    is Node.Pair -> 3 * this.left!!.magnitude() + 2 * this.right!!.magnitude()
}

private fun Node.explode() {
    while (true) {
        val pair = findExplodingPair() ?: break
        println(this)
        println("Pair: $pair")
        pair.addLeft((pair.left as Node.Number))
        pair.addRight((pair.right as Node.Number))
        if (pair.parent!!.left == pair) pair.parent!!.left = Node.Number(0)
        else pair.parent!!.right = Node.Number(0)
    }
    println(this)
    if (this.split()) this.explode()
}

private fun Node.split(): Boolean {
    if (this is Node.Pair) {
        (this.left as? Node.Number)?.let {
            if (it.number >= 10) {
                println("Split: $it")
                this.left = Node.Pair(Node.Number(it.number / 2), Node.Number((it.number + 1) / 2), this)
                return true
            }
        }
        if (this.left!!.split()) {
            return true
        } else {
            (this.right as? Node.Number)?.let {
                if (it.number >= 10) {
                    println("Split: $it")
                    this.right = Node.Pair(Node.Number(it.number / 2), Node.Number((it.number + 1) / 2), this)
                    return true
                }
            }
            return this.right!!.split()
        }
    }
    return false
}

private fun Node.Pair.addRight(value: Node.Number) {
    val p = parent ?: return
    when (val r = p.right) {
        this -> p.addRight(value)
        is Node.Number -> p.right = Node.Number(r.number + value.number)
        is Node.Pair -> r.addToLeftMost(value)
    }
}

private fun Node.Pair.addLeft(value: Node.Number) {
    val p = parent ?: return
    when (val l = p.left) {
        this -> p.addLeft(value)
        is Node.Number -> p.left = Node.Number(l.number + value.number)
        is Node.Pair -> l.addToRightMost(value)
    }
}

private fun Node.Pair.addToRightMost(value: Node.Number) {
    when (val r = right) {
        is Node.Number -> right = Node.Number(r.number + value.number)
        is Node.Pair -> r.addToRightMost(value)
        else -> {
        }
    }
}

private fun Node.Pair.addToLeftMost(value: Node.Number) {
    when (val l = left) {
        is Node.Number -> left = Node.Number(l.number + value.number)
        is Node.Pair -> l.addToLeftMost(value)
        else -> {
        }
    }
}

private fun Node.findExplodingPair(depth: Int = 1): Node.Pair? {
    if (this is Node.Pair) {
        if (depth == 5) return this
        return left?.findExplodingPair(depth + 1) ?: right?.findExplodingPair(depth + 1)
    } else return null
}

fun Node.copy(parent: Node.Pair? = null): Node = when (this) {
    is Node.Number -> Node.Number(this.number)
    is Node.Pair -> Node.Pair().also {
        it.left = this.left?.copy(parent = it)
        it.right = this.right?.copy(parent = it)
        it.parent = parent
    }
}

fun part2(input: List<String>): Long {
    val trees: List<Node.Pair> = input.map { readTree(it) }

    var maxMagnitude = 0L
    for (t1 in trees) {
        for (t2 in trees) {
            if (t1 != t2)  {
                val sum = (t1.copy() + t2.copy())
                sum.explode()
                val magnitude = sum.magnitude()
                maxMagnitude = max(maxMagnitude, magnitude)
            }
        }
    }
    return maxMagnitude
}

fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 4140L)
    check(part2(testInput) == 3993L)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
