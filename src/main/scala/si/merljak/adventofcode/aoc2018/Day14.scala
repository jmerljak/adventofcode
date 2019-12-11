package si.merljak.adventofcode.aoc2018

import scala.collection.mutable

object Day14 {

  val input = 190221

  def main(args: Array[String]): Unit = {
    var recipes: mutable.ArrayBuffer[Int] = mutable.ArrayBuffer.apply(3, 7)
    var i1 = 0
    var i2 = 1

    // part 1
    while (recipes.length < input + 10) {
      val score1 = recipes(i1)
      val score2 = recipes(i2)
      val sum = score1 + score2

      if (sum > 9) recipes.append(sum / 10)
      recipes.append(sum % 10)

      i1 = (i1 + 1 + score1) % recipes.length
      i2 = (i2 + 1 + score2) % recipes.length
    }

    println("scores of next 10 recipes = " + recipes.takeRight(10).mkString("")) // 1191216109

    // part 2
    recipes = mutable.ArrayBuffer.apply(3, 7)
    i1 = 0
    i2 = 1

    var proceed = true
    while (proceed) {
      val score1 = recipes(i1)
      val score2 = recipes(i2)
      val sum = score1 + score2

      if (sum > 9) {
        recipes.append(sum / 10)
        proceed = notFound(recipes)
      }

      if (proceed) {
        recipes.append(sum % 10)
        proceed = notFound(recipes)

        i1 = (i1 + 1 + score1) % recipes.length
        i2 = (i2 + 1 + score2) % recipes.length
      }
    }

    val iterations = recipes.mkString("").indexOf(input.toString)

    println("iterations before perfection = " + iterations) // 20268576
  }

  def notFound(recipes: mutable.ArrayBuffer[Int]): Boolean = {
    recipes.takeRight(6).mkString("") != input.toString
  }
}
