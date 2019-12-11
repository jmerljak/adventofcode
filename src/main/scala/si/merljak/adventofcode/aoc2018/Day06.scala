package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

object Day06 {
  def main(args: Array[String]): Unit = {
    val coordinates = Utils.getLines("aoc2018/input06")
      .map(line => {
        val i = line.indexOf(", ")
        val x = line.substring(0, i)
        val y = line.substring(i + 2)
        (x.toInt, y.toInt)
      })

    // part 1
    val xRange = coordinates.map(_._1).max + coordinates.map(_._1).min
    val yRange = coordinates.map(_._2).max + coordinates.map(_._2).min

    val area = for {
      x <- Range(0, xRange)
      y <- Range(0, yRange)
    } yield (x, y)

    val partial = area
      .map(a => (a, closestCoordinate(a, coordinates)))
      .filter(_._2.isDefined)
      .map(pair => (pair._1, pair._2.get))

    val exclusions = partial
      // dummy logic: coordinates that are the closest to the edge, have infinite area
      .filter(pair => pair._1._1 == 0 || pair._1._1 == xRange - 1 || pair._1._2 == 0 || pair._1._2 == yRange - 1)
      .map(_._2)
      .distinct

    val largestArea = partial
      .filterNot(pair => exclusions.contains(pair._2))
      .groupBy(x => x._2) // List(c1 ->, ...)
      .mapValues(_.size)
      .maxBy(_._2)
      ._2

    println("largest area = %s".format(largestArea)) // 4398

    // part 2
    val safeArea = area
      .map(a => sumOfDistances(a, coordinates))
      .count(_ < 10000)

    println("safe area = %s".format(safeArea)) // 4398
  }

  def taxicabDistance(p1: (Int, Int), p2: (Int, Int)): Int = {
    Math.abs(p1._1 - p2._1) + Math.abs(p1._2 - p2._2)
  }

  def closestCoordinate(a: (Int, Int), coordinates: Seq[(Int, Int)]): Option[(Int, Int)] = {
    val distances: Seq[((Int, Int), Int)] = coordinates
      // sorted distances to coordinates
      .map(c => (c, taxicabDistance(a, c)))
      .sortBy(_._2)

    // must not be equally far from two (or more) coordinates
    if (distances.head._2 != distances(1)._2)
      Some(distances.head._1)
    else
      None
  }

  def sumOfDistances(a: (Int, Int), coordinates: Seq[(Int, Int)]): Int = {
    coordinates
      .map(taxicabDistance(a, _))
      .sum
  }
}
