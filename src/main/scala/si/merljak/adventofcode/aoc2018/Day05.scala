package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.util.matching.Regex

object Day05 {

  val adjacentPattern: Regex = "(\\w)(?i)(\\1)".r

  def main(args: Array[String]): Unit = {
    val polymer = Utils.getLines("aoc2018/input05").head

    // part 1
    val reacted = react(polymer)
    println("polymer size after reaction = %d".format(reacted.length)) // 11042

    // part 2
    val shortestPolymer = polymer.distinct
      .toLowerCase.distinct
      .map(x => polymer.replaceAll("(?i)[%s]".format(x), ""))
      .map(react)
      .minBy(_.length)
      .length

    println("shortest polymer length = %d".format(shortestPolymer)) // 6872
  }

  def react(polymer: String): String = {
    var result = polymer

    var unit = findAdjacentUnit(result)
    while (unit.isDefined) {
      result = result.replaceFirst(unit.get, "")
      unit = findAdjacentUnit(result)
    }

    result
  }

  def findAdjacentUnit(polymer: String): Option[String] = {
    adjacentPattern.findAllIn(polymer)
      .find(mtch => {
        val c1 = mtch.charAt(0)
        val c2 = mtch.charAt(1)

        // additional filter
        c1.isLower && c2.isUpper ||
          c1.isUpper && c2.isLower
      })
  }
}
