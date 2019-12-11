package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.util.matching.Regex

object Day10 {

  val pattern: Regex = "position=<([\\s|\\-][0-9]+), ([\\s|\\-][0-9]+)> velocity=<([\\s|\\-][0-9]+), ([\\s|\\-][0-9]+)>".r

  def main(args: Array[String]): Unit = {
    val data = Utils.getLines("aoc2018/input10")
      .map(pattern.findFirstMatchIn(_).get)
      .map(matched => (
        (matched.group(1).trim.toInt, matched.group(2).trim.toInt),
        (matched.group(3).trim.toInt, matched.group(4).trim.toInt)
      ))

    val positions = data.map(_._1).toArray
    val velocities = data.map(_._2).toArray

    var minYDiff = yDiff(positions)
    var proceed = true
    var seconds = 0
    while (proceed) {
      // calculate new positions
      velocities.indices
        .foreach(i => {
          val pos = positions(i)
          val vel = velocities(i)
          positions(i) = (pos._1 + vel._1, pos._2 + vel._2)
        })

      // check if reached minimum
      val newYDiff = yDiff(positions)
      if (minYDiff < newYDiff) {
        // one step back
        velocities.indices
          .foreach(i => {
            val pos = positions(i)
            val vel = velocities(i)
            positions(i) = (pos._1 - vel._1, pos._2 - vel._2)
          })

        proceed = false
      } else {
        minYDiff = newYDiff
        seconds += 1
      }
    }

    // part 1
    println("message =")
    draw(positions) // PHLGRNFK

    // part 2
    println("seconds = %d".format(seconds)) // 10407
  }

  def yDiff(positions: Seq[(Int, Int)]): Int = {
    val ys = positions.map(_._2)
    ys.max - ys.min
  }

  def draw(positions: Seq[(Int, Int)]): Unit = {
    val xs = positions.map(_._1)
    val ys = positions.map(_._2)

    for (y <- ys.min to ys.max) {
      for (x <- xs.min to xs.max) {
        if (positions.contains((x, y))) print("#") else print(".")
      }
      println
    }
  }
}
