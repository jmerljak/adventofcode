package si.merljak.adventofcode.aoc2018

import scala.collection.mutable

object Day11 {

  val serialNumber = 9995

  def main(args: Array[String]): Unit = {
    val scores: mutable.Map[(Int, Int, Int), Long] = mutable.Map.empty

    for (size <- 1 to 300) {
      // println(size)
      for (x <- 1 to 301 - size) {
        for (y <- 1 to 301 - size) {
          val key = (x, y, size)
          if (size == 1) {
            scores.put(key, powerLevel(x, y))
          } else {
            val previousSize = size - 1
            val addition = (x to x + previousSize).map(powerLevel(_, y + previousSize)).sum +
              (y until y + previousSize).map(powerLevel(x + previousSize, _)).sum
            scores.put(key, scores.getOrElse((x, y, previousSize), 0L) + addition)
          }
        }
      }
    }

    // part 1
    val bestOfSize3 = scores.filter(_._1._3 == 3).maxBy(_._2)._1
    println("best square of size 3 = %d,%d".format(bestOfSize3._1, bestOfSize3._2)) // 33,45

    // part 2
    val bestOfAll = scores.maxBy(_._2)._1
    println("best square of any size = %d,%d,%d".format(bestOfAll._1, bestOfAll._2, bestOfAll._3)) // 233,116,15
  }

  def powerLevel(cell: (Int, Int)): Int = {
    val rackId = cell._1 + 10
    ((((rackId * cell._2) + serialNumber) * rackId) / 100) % 10 - 5
  }
}
