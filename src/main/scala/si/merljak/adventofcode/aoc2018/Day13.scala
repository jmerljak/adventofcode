package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

import scala.collection.mutable

object Day13 {
  def main(args: Array[String]): Unit = {
    val data = Utils.getLines("aoc2018/input13")

    val initialState: Map[(Int, Int), Char] = data.indices
      .flatMap(y => data(y).indices.map(x => ((x, y), data(y)(x))))
      .toMap

    val tracks: Map[(Int, Int), Char] = initialState
      .filterNot(_._2 == ' ')
      .mapValues(track =>
        if (track == '^' || track == 'v') '|'
        else if (track == '<' || track == '>') '-'
        else track)

    var carts: Map[(Int, Int), (Char, Int)] = initialState
      .filter(c => Set('^', 'v', '<', '>').contains(c._2))
      .map(chart => (chart._1, (chart._2, 0)))

    // part 1
    var result: Either[Map[(Int, Int), (Char, Int)], (Int, Int)] = Left(carts)
    while (result.isLeft) {
      result = tick(result.left.get, tracks)
    }

    println("collision coordinates = %d,%d".format(result.right.get._1, result.right.get._2)) // 14,42

    // part 2
    while (carts.size > 1) {
      carts = tickAndRemove(carts, tracks)
    }

    println("last cart coordinates = %d,%d".format(carts.head._1._1, carts.head._1._2)) // 8,7
  }

  def tick(carts: Map[(Int, Int), (Char, Int)], tracks: Map[(Int, Int), Char]): Either[Map[(Int, Int), (Char, Int)], (Int, Int)] = {
    val newCarts: mutable.Map[(Int, Int), (Char, Int)] = mutable.Map.empty ++ carts

    carts
      .toList
      .sortBy(cart => (cart._1._2, cart._1._1)) // sort by coordinate
      .foreach(cart => {
      val coordinates = cart._1
      val direction = cart._2._1
      val turnPreference = cart._2._2

      val newCoordinates = move(coordinates, direction)
      val newDirectionAndTurnPreference = turn(newCoordinates, direction, tracks, turnPreference)

      newCarts.remove(coordinates)
      if (newCarts.contains(newCoordinates)) {
        return Right(newCoordinates)
      }

      newCarts.put(newCoordinates, newDirectionAndTurnPreference)
    })

    Left(newCarts.toMap)
  }

  def tickAndRemove(carts: Map[(Int, Int), (Char, Int)], tracks: Map[(Int, Int), Char]): Map[(Int, Int), (Char, Int)] = {
    val newCarts: mutable.Map[(Int, Int), (Char, Int)] = mutable.Map.empty ++ carts
    val removed: mutable.Set[(Int, Int)] = mutable.Set.empty

    carts
      .toList
      .sortBy(cart => (cart._1._2, cart._1._1)) // sort by coordinate
      .foreach(cart => {
      if (!removed.contains(cart._1)) {
        val coordinates = cart._1
        val direction = cart._2._1
        val turnPreference = cart._2._2

        val newCoordinates = move(coordinates, direction)
        val newDirectionAndTurnPreference = turn(newCoordinates, direction, tracks, turnPreference)

        newCarts.remove(coordinates)
        if (newCarts.contains(newCoordinates)) {
          removed.add(newCoordinates)
          newCarts.remove(newCoordinates)
        } else {
          newCarts.put(newCoordinates, newDirectionAndTurnPreference)
        }
      }
    })

    newCarts.toMap
  }

  def move(coordinates: (Int, Int), direction: Char): (Int, Int) = {
    if (direction == '<')
      (coordinates._1 - 1, coordinates._2)
    else if (direction == '>')
      (coordinates._1 + 1, coordinates._2)
    else if (direction == '^')
      (coordinates._1, coordinates._2 - 1)
    else if (direction == 'v')
      (coordinates._1, coordinates._2 + 1)
    else
      throw new IllegalStateException("illegal direction")
  }

  def turn(coordinates: (Int, Int), direction: Char, tracks: Map[(Int, Int), Char], turnPreference: Int): (Char, Int) = {
    var newTurnPreferences = turnPreference
    val newDirection: Char = tracks.get(coordinates) match {
      case Some('\\') =>
        if (direction == 'v') '>'
        else if (direction == '^') '<'
        else if (direction == '>') 'v'
        else /*if (direction == '<')*/ '^'
      case Some('/') =>
        if (direction == 'v') '<'
        else if (direction == '^') '>'
        else if (direction == '>') '^'
        else /*if (direction == '<')*/ 'v'
      case Some('+') =>
        newTurnPreferences = (turnPreference + 1) % 3
        if (direction == 'v') {
          turnPreference match {
            case 0 => '>'
            case 1 => direction
            case 2 => '<'
          }
        }
        else if (direction == '^') {
          turnPreference match {
            case 0 => '<'
            case 1 => direction
            case 2 => '>'
          }
        }
        else if (direction == '>') {
          turnPreference match {
            case 0 => '^'
            case 1 => direction
            case 2 => 'v'
          }
        }
        else /*if (direction == '<')*/ {
          turnPreference match {
            case 0 => 'v'
            case 1 => direction
            case 2 => '^'
          }
        }
      case Some(_) =>
        direction
      case None =>
        throw new IllegalStateException("illegal track")
    }

    (newDirection, newTurnPreferences)
  }
}
