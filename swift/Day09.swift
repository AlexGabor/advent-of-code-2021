import Foundation

@main
struct Day02 {
    
    static func part1(input: Array<String>) -> Int {
        var risk = 0
        for (i, _) in input.enumerated() {
            for (j, _) in input[i].enumerated() {
                if (i == 0 || input[i][j] < input[i - 1][j]) &&
                        (i == input.count - 1 || input[i][j] < input[i + 1][j]) &&
                        (j == 0 || input[i][j] < input[i][j - 1]) &&
                        (j == input[i].count - 1 || input[i][j] < input[i][j + 1]) {
                    risk += 1 + Int(String(input[i][j]))!
                }
            }
        }
        return risk
    }
    
    static func part2(input: Array<String>) -> Int {
        var cave = Array<Array<Int>>(repeating: Array(repeating: 0, count: input[0].count), count: input.count)
        
        for (i, _) in input.enumerated() {
            for (j, _) in input[i].enumerated() {
                cave[i][j] = Int(String(input[i][j]))!
            }
        }
        
        var basins = Array<Int>()
        for i in 0..<cave.count {
            for j in 0..<cave[i].count {
                if cave.isLowest(i: i, j: j) {
                    basins.append(cave.fillBasin(i, j))
                }
            }
        }
        
        return basins.sorted().suffix(3).reduce(1) { (acc, value) in acc * value }
    }
    
    static func main() {
        let testInput = Utils.readInput(name: "Day09_test")
        assert(part1(input: testInput) == 15)
        assert(part2(input: testInput) == 1134)
        
        let input = Utils.readInput(name: "Day09")
        print(part1(input: input), terminator: "\n")
        print(part2(input: input), terminator: "\n")
    }
}

private extension Array where Element == Array<Int> {
    func isLowest(i: Int, j: Int) -> Bool {
        return (i == 0 || self[i][j] < self[i - 1][j]) &&
        (i == self.count - 1 || self[i][j] < self[i + 1][j]) &&
        (j == 0 || self[i][j] < self[i][j - 1]) &&
        (j == self[i].count - 1 || self[i][j] < self[i][j + 1])
    }
    
    mutating func fillBasin(_ i: Int, _ j: Int) -> Int {
        if self.isOutOfBounds(i, j) || self[i][j] == 9 {
            return 0
        }
        self[i][j] = 9
        return 1 + fillBasin(i + 1, j) + fillBasin(i - 1, j) + fillBasin(i, j + 1) + fillBasin(i, j - 1)
    }
    
    func isOutOfBounds(_ i: Int, _ j: Int) -> Bool {
        return (i == -1 || i == self.count || j == -1 || j == self[i].count)
    }
}
