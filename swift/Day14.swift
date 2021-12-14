import Foundation

struct CacheKey: Hashable {
    let polymer: String
    let step: Int
}

var cache: Dictionary<CacheKey, Dictionary<Character, UInt64>> = [:]

@main
struct Day14 {
    

    static func part2(input: Array<String>) -> UInt64 {
        let template: Array<Character> = input[0].map { value in value}
        
        var pairs: Dictionary<String, Character> = [:]
        input.suffix(input.count - 1).forEach { value in
            let line = value.components(separatedBy: " -> ")
//            let pair, insert = line
            pairs[line[0]] = line[1].first!
        }
        
        var count: Dictionary<Character, UInt64> = [template.last!: 1]
        for i in 0..<(template.count-1) {
            let sequenceCount = grow("\(template[i])\(template[i+1])", 40, pairs)
            count.merge(sequenceCount) { $0 + $1 }
        }
        
        let max: UInt64 = count.max { $0.value < $1.value }?.value ?? 0
        let min: UInt64 = count.min { $0.value < $1.value }?.value ?? 0
        return max - min
    }
    
    static func grow(_ polymer: String, _ step: Int, _ pairs: Dictionary<String, Character>) -> Dictionary<Character, UInt64> {
        guard step != 0 else { return [polymer[0]: 1] }
    
        if let cached = cache[CacheKey(polymer: polymer, step: step)] {
            return cached
        }
        
        if let insert = pairs[polymer] {
            let left = grow("\(polymer[0])\(insert)", step-1, pairs)
            let right = grow("\(insert)\(polymer[1])", step-1, pairs)
            let count = left.merging(right) { (old, new) in old + new}
            cache[CacheKey(polymer: polymer, step: step)] = count
            return count
        }
        return [polymer[0]: 1]
    }

    
    static func main() {
//        let testInput = Utils.readInput(name: "Day09_test")
//        assert(part1(input: testInput) == 1588)
//        assert(part2(input: testInput) == 1134)
        
        let input = Utils.readInput(name: "Day14")
//        print(part1(input: input), terminator: "\n")
        print(part2(input: input), terminator: "\n")
    }
}
