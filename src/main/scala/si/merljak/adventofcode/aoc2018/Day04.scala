package si.merljak.adventofcode.aoc2018

import java.time.format.DateTimeFormatter
import java.time.{Duration, LocalDateTime}

import si.merljak.adventofcode.Utils

import scala.util.matching.Regex

object Day04 {

  val recordPattern: Regex = "\\[(1518-[0-9]+-[0-9]+ [0-9]+:[0-9]+)\\] ([a-zA-Z#0-9 ]+)".r
  val shiftChangePattern: Regex = "Guard #([0-9]+) begins shift".r
  val timestampFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  def main(args: Array[String]): Unit = {
    val records = Utils.getLines("aoc2018/input04")
      .map(recordPattern.findFirstMatchIn(_).get)
      .map(matched => {
        val timestamp = LocalDateTime.parse(matched.group(1), timestampFormat)
        val value = matched.group(2)
        (timestamp, value)
      })
      .sortBy(_._1)(DateTimeOrdering)

    // part 1
    var guard: Int = -1
    var fallsAsleep: LocalDateTime = null
    var sleeps: Map[Int, Map[Int, Int]] = Map.empty

    records.foreach(record => {
      record._2 match {
        case "falls asleep" => fallsAsleep = record._1
        case "wakes up" =>
          var entry = sleeps.getOrElse(guard, Map.empty)
          for (m <- fallsAsleep.getMinute until record._1.getMinute) {
            entry = entry + (m -> (entry.getOrElse(m, 0) + 1))
          }

          sleeps = sleeps + (guard -> entry)
        case value: String => guard = shiftChangePattern.findFirstMatchIn(value).get.group(1).toInt
      }
    })

    val topSleeperData = sleeps.maxBy(_._2.values.sum)
    val topSleeper = topSleeperData._1
    val topMinute = topSleeperData._2.maxBy(_._2)._1

    val sleepRatio1 = topSleeper * topMinute
    println("sleepRatio1 = %d".format(sleepRatio1)) // 119835

    // part 2
    val sleeperData = sleeps.mapValues(_.maxBy(_._2))
      .maxBy(_._2._2)

    val sleeper = sleeperData._1
    val minute = sleeperData._2._1

    val sleepRatio2 = sleeper * minute
    println("sleepRatio2 = %d".format(sleepRatio2)) // 12725
  }

  implicit object DateTimeOrdering extends Ordering[LocalDateTime] {
    override def compare(x: LocalDateTime, y: LocalDateTime): Int = x compareTo y
  }

  implicit object DurationOrdering extends Ordering[Duration] {
    override def compare(x: Duration, y: Duration): Int = x compareTo y
  }

}
