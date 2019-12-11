package si.merljak.adventofcode.aoc2018

import si.merljak.adventofcode.Utils

object Day16 {

  var registers: Array[Int] = Array(0, 0, 0, 0)

  def main(args: Array[String]): Unit = {
    val data = Utils.getLines("aoc2018/input16")

    val operations: Set[(Int, Int, Int) => Unit] =
      Set(
        addr, addi, mulr, muli,
        banr, bani, borr, bori,
        setr, seti,
        gtir, gtri, gtrr,
        eqir, eqri, eqrr
      )

    // part 1
    val samples = data.take(3260).grouped(4)
      .map(seq => {
        val before = seq.head.substring(seq.head.indexOf('[') + 1, seq.head.indexOf(']'))
          .split(", ").map(_.toInt)
        val instructions = seq(1)
          .split(' ').map(_.toInt)
        val after = seq(2).substring(seq(2).indexOf('[') + 1, seq(2).indexOf(']'))
          .split(", ").map(_.toInt)

        val candidates = operations
          .filter(operation => {
            before.copyToArray(registers)
            operation.apply(instructions(1), instructions(2), instructions(3))
            registers sameElements after // do check
          })

        val code = instructions.head
        (code, candidates)
      })
      .toList

    // part 1
    val num = samples.count(_._2.size > 2)

    println("number of samples = %d".format(num)) // 663

    // part 2
    var codeToOp: Map[Int, Set[(Int, Int, Int) => Unit]] = samples
      .groupBy(_._1) // 1 -> ((1, (op1, op2)), (1, (op2, op3)))
      .mapValues(s => s.map(_._2.toSet)) // 1 -> ((op1, op2), (op2, op3))
      .mapValues(s => s.reduce((s1, s2) => s1.intersect(s2)))

    while (codeToOp.exists(_._2.size > 1)) {
      var resolved = codeToOp.filter(_._2.size == 1).mapValues(_.head)

      codeToOp = codeToOp.mapValues(ops => {
        if (ops.size == 1) ops
        else ops.filterNot(op => resolved.values.exists(_ == op))
      })
    }

    registers = Array(0, 0, 0, 0)
    data.drop(3262)
      .foreach(line => {
        val instruction = line.split(' ').map(_.toInt)
        codeToOp(instruction.head).head
          .apply(instruction(1), instruction(2), instruction(3))
      })

    println("register 0 value = %d".format(registers(0))) // 525
  }

  def addr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) + registers(b)
  }

  def addi(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) + b
  }

  def mulr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) * registers(b)
  }

  def muli(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) * b
  }

  def banr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) & registers(b)
  }

  def bani(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) & b
  }

  def borr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) | registers(b)
  }

  def bori(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a) | b
  }

  def setr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = registers(a)
  }

  def seti(a: Int, b: Int, c: Int): Unit = {
    registers(c) = a
  }

  def gtir(a: Int, b: Int, c: Int): Unit = {
    registers(c) = if (a > registers(b)) 1 else 0
  }

  def gtri(a: Int, b: Int, c: Int): Unit = {
    registers(c) = if (registers(a) > b) 1 else 0
  }

  def gtrr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = if (registers(a) > registers(b)) 1 else 0
  }

  def eqir(a: Int, b: Int, c: Int): Unit = {
    registers(c) = if (a == registers(b)) 1 else 0
  }

  def eqri(a: Int, b: Int, c: Int): Unit = {
    registers(c) = if (registers(a) == b) 1 else 0
  }

  def eqrr(a: Int, b: Int, c: Int): Unit = {
    registers(c) = if (registers(a) == registers(b)) 1 else 0
  }
}
