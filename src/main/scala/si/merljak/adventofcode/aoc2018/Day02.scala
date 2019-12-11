package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

object Day02 {
  def main(args: Array[String]): Unit = {
    val codes = Utils.getLines("aoc2018/input02")

    // part 1
    val maps = codes
      .map(code => (code, code
        .groupBy(identity)
        .filter((tuple: (_, String)) => tuple._2.length == 2 || tuple._2.length == 3)
        .map((tuple: (Char, String)) => (tuple._1, tuple._2.length))
      ))
      .toMap

    // println(maps)

    val nOfDoubles = maps.groupBy(doubles orElse other)("Doubles").size
    val nOfTriples = maps.groupBy(triples orElse other)("Triples").size
    val checksum = nOfDoubles * nOfTriples

    println("checksum = %d".format(checksum)) // 9633

    // part 2
    val relevantCodes = maps.keySet
    val pair = findCodesDifferInExactlyOneChar(relevantCodes)
    val diff = findDiffIndexes(pair._1, pair._2)
    val commonLetters = pair._1.substring(0, diff.head) concat pair._1.substring(diff.head + 1, pair._1.length)

    println("commonLetters = %s".format(commonLetters)) // lujnogabetpmsydyfcovzixaw
  }

  val identity: PartialFunction[Char, Char] = {
    case x: Char => x
  }

  val doubles: PartialFunction[(String, Map[Char, Int]), String] = {
    case x: (String, Map[Char, Int]) if x._2.exists((tuple: (Char, Int)) => tuple._2 == 2) ⇒ "Doubles"
  }

  val triples: PartialFunction[(String, Map[Char, Int]), String] = {
    case x: (String, Map[Char, Int]) if x._2.exists((tuple: (Char, Int)) => tuple._2 == 3) ⇒ "Triples"
  }

  val other: PartialFunction[(String, Map[Char, Int]), String] = {
    case _: (String, Map[Char, Int]) ⇒ "Other"
  }

  def findCodesDifferInExactlyOneChar(codes: Set[String]): (String, String) = {
    for (c1 <- codes) {
      for (c2 <- codes) {
        val diff = findDiffIndexes(c1, c2)

        if (diff.size == 1)
          return (c1, c2)
      }
    }

    null
  }

  def findDiffIndexes(c1: String, c2: String): Set[Int] = {
    var diff: Set[Int] = Set.empty
    for (x <- 0 until c1.length) {
      if (c1(x) != c2(x)) {
        diff = diff.+(x)
      }
    }

    diff
  }
}
