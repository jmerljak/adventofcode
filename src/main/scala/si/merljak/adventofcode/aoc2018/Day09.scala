package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.collection.mutable
import scala.util.matching.Regex

object Day09 {

  val denominator = 23
  val pattern: Regex = "([0-9]+) players; last marble is worth ([0-9]+) points".r

  def main(args: Array[String]): Unit = {
    val (players, marbles): (Int, Int) = Utils.getLines("aoc2018/input09")
      .map(pattern.findFirstMatchIn(_).get)
      .map(matched => (matched.group(1).toInt, matched.group(2).toInt))
      .head

    // part 1
    val score1 = play(players, marbles)
    println("winning score 1 = %d".format(score1)) // 410375

    // part 2 (sub-optimal solution)
    val score2 = play(players, marbles * 100)
    println("winning score 2 = %d".format(score2)) // 3314195047
  }

  def play(players: Int, marbles: Int): Long = {
    val circle: mutable.ArrayBuffer[Int] = mutable.ArrayBuffer.apply(0)
    val scores: mutable.Map[Int, Long] = mutable.Map.empty

    var current = 0
    var player = 0

    Range.inclusive(1, marbles)
      .foreach(marble => {
        if (marble % denominator == 0) {
          current = (current - 7 + circle.size) % circle.size
          scores.update(player, scores.getOrElse(player, 0L) + marble + circle.remove(current))
        } else {
          current = (current + 2) % circle.size
          if (current == 0) {
            current = circle.size
            circle.append(marble)
          } else {
            circle.insert(current, marble)
          }
        }
        player = (player + 1) % players

//        if (marble % 25000 == 0) {
//          println("progress = %s".format(marble * 100.0 / marbles))
//        }

//        println("[%d] %s".format(marble, circle.map(i => {
//          if (i == circle(current)) "(%d)".format(i) else " %d".format(i)
//        })))
      })

    scores.values.max
  }
}
