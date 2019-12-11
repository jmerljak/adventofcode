package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.collection.mutable
import scala.util.matching.Regex

object Day07 {

  val recordPattern: Regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.".r

  def main(args: Array[String]): Unit = {
    val instructions: Seq[(String, String)] = Utils.getLines("aoc2018/input07")
      .map(recordPattern.findFirstMatchIn(_).get)
      .map(matched => (matched.group(1), matched.group(2))) // prerequisite step, step

    val dependencies: Map[String, Seq[String]] = instructions
      .groupBy(_._2)
      .mapValues(_.map(_._1)) ++
      // add steps with no dependencies
      instructions.map(_._1)
        .filterNot(i => instructions.exists(_._2 == i))
        .map(_ -> Seq.empty)

    // part 1
    var remaining = dependencies
    var order = ""
    while (remaining.nonEmpty) {
      val nextStep = remaining.filter(_._2.isEmpty).keys.min
      remaining = remaining - nextStep
      remaining = remaining.mapValues(_.filterNot(_ == nextStep))
      order = order + nextStep
    }

    println("order = %s".format(order)) // GJFMDHNBCIVTUWEQYALSPXZORK

    // part 2
    var unfinished = dependencies
    var duration = -1
    val scheduler: mutable.Map[Int, (String, Int)] = mutable.Map.empty

    while (unfinished.nonEmpty) {
      // decrease remaining time
      for (i <- Range(0, 5)) {
        val option = scheduler.get(i)
        if (option.isDefined) {
          val step = option.get._1
          val remainingTime = option.get._2
          if (remainingTime > 1) {
            scheduler.put(i, (step, remainingTime - 1))
          } else {
            unfinished = unfinished - step
            unfinished = unfinished.mapValues(_.filterNot(_ == step))
            scheduler.remove(i)
          }
        }
      }

      // schedule task to idle workers
      for (i <- Range(0, 5)) {
        if (!scheduler.contains(i)) {
          val optionalStep = unfinished
            .filter(_._2.isEmpty)
            .filterNot(x => scheduler.exists(_._2._1 == x._1))
            .keySet.toList.sorted
            .headOption

          if (optionalStep.isDefined) {
            val step = optionalStep.get
            scheduler.put(i, (step, step.charAt(0).toInt - 4)) // A = 65, B = 66, ...
          }
        }
      }

      duration += 1
    }

    println("duration = %d".format(duration)) // 1050
  }
}
