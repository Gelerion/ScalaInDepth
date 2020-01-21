package programming.in.scala.advanced.cats.functional.case_study.crdt.v1

import scala.collection.mutable

/*
GCounters allow each machine to keep an accurate account of the state of the whole system without storing
the complete history of interactions. If a machine wants to calculate the total traffic for the whole web site,
it sums up all the per-machine counters. The result is accurate or near-accurate depending on how recently
we performed a reconciliation. Eventually, regardless of network outages, the system will always
converge on a consistent state.
 */
final case class GCounterV1(counters: mutable.Map[String, Int]) {

  def increment(machine: String, amount: Int): GCounterV1 = {
    val value = amount + counters.getOrElse(machine, 0)
    GCounterV1(counters + (machine -> value))
  }

  def merge(that: GCounterV1): GCounterV1 =
    GCounterV1(that.counters ++ this.counters.map {
      case (k, v) =>
        k -> (v max that.counters.getOrElse(k, 0))
    })

  def total: Int = counters.values.sum

/* My implementation:
  def increment(machine: String, amount: Int): Unit =
    counters.get(machine) match {
      case Some(prev) => counters += (machine -> (prev + amount))
      case None => counters += (machine -> amount)
    }

  def merge(that: GCounterV1): GCounterV1 = {
    that.counters.foreach { case (m1, a1) =>
      this.counters.get(m1) match {
        case Some(value) => if (a1 > value) increment(m1, (a1 - value))
        case None => increment(m1, a1)
      }
    }

    this
  }

  def total: Int = counters.values.sum*/
}

object TestGCounterV1 {
  def main(args: Array[String]): Unit = {
    var A = GCounterV1(mutable.Map("A" -> 0, "B" -> 0))
    var B = GCounterV1(mutable.Map("A" -> 0, "B" -> 0))

    A = A.increment("A", 3)
    B = B.increment("B", 2)
    println("A.total == 3? " + A.total)
    println("B.total == 2? " + B.total)

    println("Merging A with B")
    A = A.merge(B)
    B = B.merge(A)

    println("A.counters = " + A.counters)
    println("B.counters = " + B.counters)
    println("A.total = 5? " + A.total)
    println("B.total = 5? " + B.total)

    println("Update A")
    A = A.increment("A", 1)
    println("Merging A with B")
    A = A.merge(B)
    B = B.merge(A)

    println("A.counters = " + A.counters)
    println("B.counters = " + B.counters)
    println("A.total = 6? " + A.total)
    println("B.total = 6? " + B.total)
  }
}