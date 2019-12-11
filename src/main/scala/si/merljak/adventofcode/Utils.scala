package si.merljak.adventofcode

import scala.io.{BufferedSource, Source}

object Utils {

  def getLines(filename: String): Seq[String] = {
    val source: BufferedSource = Source.fromResource(filename)
    val lines: Seq[String] = source.getLines().toList
    source.close

    lines
  }
}
