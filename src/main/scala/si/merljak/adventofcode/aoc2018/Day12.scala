package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.util.matching.Regex

object Day12 {

  def pattern: Regex = "([.#]+) => ([.#])".r

  def main(args: Array[String]): Unit = {
    val data = Utils.getLines("aoc2018/input12")
    val initialState = data.head.replace("initial state: ", "")
    val rules = data.drop(2)
      .map(pattern.findFirstMatchIn(_).get)
      .map(matched => (matched.group(1), matched.group(2)))
      .toMap

    // part 1
    var generations = 20
    var state = grow(initialState, rules, generations)
    var sum = sumPots(state, generations)

    println("sum1 = %d".format(sum)) // 1447

    // part 2
    // by observing it seems like it converges after approximately 100 generations
    // every next generation the sum just increases by the number of plants
    generations = 100
    state = grow(initialState, rules, generations)
    sum = sumPots(state, generations) + (50000000000L - generations) * state.count(_ == '#')

    println("sum2 = %d".format(sum)) // 1050000000480
  }

  def grow(initialState: String, rules: Map[String, String], generations: Int): String = {
    var state = initialState

    for (g <- 1 to generations) {
      // println(("%" + (state.length + 2 * (generations - g + 1)) + "s").format(state))
      val temp = "...." + state + "...."

      state = Range(2, temp.length - 2)
        .map(i => rules(temp.substring(i - 2, i + 3)))
        .reduce((value: String, value1: String) => value.concat(value1))
    }
    // println(state)

    state
  }

  def sumPots(state: String, generations: Int): Long = {
    state.indices
      .filter(state(_) == '#')
      .map(_ - (2 * generations))
      .sum
  }
}
