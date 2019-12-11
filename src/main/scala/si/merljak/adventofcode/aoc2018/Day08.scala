package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

object Day08 {
  def main(args: Array[String]): Unit = {
    val data = Utils.getLines("aoc2018/input08").head
      .split("\\s")
      .map(_.toInt)

    val node = parseNode(data, 0)._1

    // part 1
    val sum = node.sumMetadata
    println("sum = %d".format(sum)) // 36307

    // part 2
    val value = node.value
    println("value = %d".format(value)) // 25154
  }

  def parseNode(data: Seq[Int], offset: Int): (Node, Int) = {
    val nChild = data(offset)
    val nMeta = data(offset + 1)

    var newOffset = offset + 2
    val children = Range(0, nChild)
      .map(_ => {
        val pair = parseNode(data, newOffset)
        newOffset = pair._2
        pair._1
      })

    val endOffset = newOffset + nMeta
    (new Node(children, data.slice(newOffset, endOffset)), endOffset)
  }

  class Node(val children: Seq[Node], val metadata: Seq[Int]) {
    override def toString: String = "c=[%s], m=[%s]".format(children, metadata)

    def sumMetadata: Int = {
      children.map(_.sumMetadata).sum + metadata.sum
    }

    def value: Int = {
      if (children.isEmpty) {
        metadata.sum
      } else {
        metadata
          .map(_ - 1) // metadata indexes start with 1
          .filter(children.isDefinedAt) // skip non existing nodes
          .map(children(_).value)
          .sum
      }
    }
  }

}
