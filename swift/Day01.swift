//
//  main.swift
//  advent-of-code
//
//  Created by Alexandru Gabor on 01.12.2021.
//

import Foundation

@main
struct Day01 {
    
    static func part1(input: Array<Int>) -> Int {
        var count = 0
        for index in 1...(input.count - 1) {
            if (input[index] > input[index - 1]) {
                count += 1
            }
        }
        return count
    }
    
    static func part2(input: Array<Int>) -> Int{
        var count = 0
        for index in 1...(input.count - 3) {
            if (input[index] + input[index + 1] + input[index + 2] > input[index - 1] + input[index] + input[index + 1]) {
                count += 1
            }
        }
        return count
    }
    
    static func main() {
        let testInput = Utils.readInput(name: "Day01_test").map { value in Int(value)! }
        assert(part1(input: testInput) == 7)
        assert(part2(input: testInput) == 5)
        
        let input = Utils.readInput(name: "Day01").map { value in Int(value)! }
        print(part1(input: input), terminator: "\n")
        print(part2(input: input), terminator: "\n")
    }
}
