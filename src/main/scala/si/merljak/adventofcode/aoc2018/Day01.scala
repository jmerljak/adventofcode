package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

object Day01 {

  var current = 0
  var frequencies: Set[Int] = Set(current)

  def main(args: Array[String]): Unit = {
    val changes: Seq[Int] = Utils.getLines("aoc2018/input01")
      .map(Integer.parseInt)

    // part 1
    val result = changes.sum
    println("result = %d".format(result)) // 406

    // part 2
    var duplicate: Option[Int] = None
    while (duplicate.isEmpty) {
      // might not be found in the first iteration
      duplicate = findDuplicate(changes)
    }

    println("duplicate = %d".format(duplicate.get)) // 312
  }

  def findDuplicate(changes: Seq[Int]): Option[Int] = {
    changes.foreach((change: Int) => {
      current = current + change
      if (frequencies.contains(current)) {
        return Some(current)
      }
      frequencies = frequencies.+(current)
    })

    None
  }
}
