//
//  Utils.swift
//  advent-of-code
//
//  Created by Alexandru Gabor on 01.12.2021.
//

import Foundation

struct Utils {
    static func readInput(name: String) -> Array<String> {
        let path = Bundle.main.path(forResource: "\(name)", ofType: "txt", inDirectory: "input")!
        return try! String(contentsOfFile: path).split(separator: "\n").map { value in
            value.description
        }
    }
}

extension StringProtocol {
    subscript(offset: Int) -> Character {
        self[index(startIndex, offsetBy: offset)]
      }
}

