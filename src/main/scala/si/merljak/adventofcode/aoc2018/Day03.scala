package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.util.matching.Regex

object Day03 {

  val pattern: Regex = "#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)".r

  def main(args: Array[String]): Unit = {
    val claims = Utils.getLines("aoc2018/input03")
      .map(pattern.findFirstMatchIn(_).get)
      .map(matched => {
        val id = matched.group(1)
        val x1 = matched.group(2).toInt
        val x2 = x1 + matched.group(4).toInt
        val y1 = matched.group(3).toInt
        val y2 = y1 + matched.group(5).toInt
        (id, x1, y1, x2, y2)
      })

    val overlaps = claims.flatMap(claim => {
      for {
        x <- Range(claim._2, claim._4)
        y <- Range(claim._3, claim._5)
      } yield (claim._1, (x, y))
    })
      .groupBy(x => x._2)
      .mapValues(_.map(_._1))

    // part 1
    val overlapping = overlaps.values
      .count(_.size > 1)

    println("overlapping = %s".format(overlapping)) // 110827

    // part 2
    val c1 = overlaps
      .filter(_._2.size > 1)
      .values
      .flatMap(_.toList)
      .toSet

    val c2 = overlaps
      .filter(_._2.size == 1)
      .values
      .flatMap(_.toList)
      .toSet

    val uniqueClaim = c2 diff c1

    println("uniqueClaim = %s".format(uniqueClaim.head)) // 116
  }
}
