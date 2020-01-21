package programming.in.scala.advanced.cats.functional.case_study.crdt.v2_generalization

import cats.instances.list._
import cats.instances.map._
import cats.kernel.CommutativeMonoid
import cats.syntax.semigroup._
import cats.syntax.foldable._  // for combineTll

final case class GCounterV2[T](counters: Map[String, T]) {
  def increment(machine: String, amount: T)(implicit m: CommutativeMonoid[T]): GCounterV2[T] = {
    val value = amount |+| counters.getOrElse(machine, m.empty)
    GCounterV2(counters + (machine -> value))
  }

  def merge(that: GCounterV2[T])(implicit b: BoundedSemiLattice[T]): GCounterV2[T] =
    GCounterV2(this.counters |+| that.counters)

  def total(implicit m: CommutativeMonoid[T]): T = this.counters.values.toList.combineAll
  
  
/* My implementation
  def increment(machine: String, amount: T)(implicit M: Monoid[T]): GCounterV2[T] = {
    val value: T = M.combine(amount, counters.getOrElse(machine, M.empty))
    GCounterV2(counters + (machine -> value))
  }

  def merge(that: GCounterV2[T])(implicit L: BoundedSemiLattice[T]): GCounterV2[T] = {
    GCounterV2(that.counters ++ this.counters.map {
      case (k, v) =>
        k -> L.combine(v, that.counters.getOrElse(k, L.empty))
    })
  }

  def total(implicit M: CommutativeMonoid[T]): T = M.combineTll(counters.values)*/
}

object TestGCounterV2 {
  def main(args: Array[String]): Unit = {
    import cats.instances.int._

    var A = GCounterV2(Map("A" -> 0, "B" -> 0))
    var B = GCounterV2(Map("A" -> 0, "B" -> 0))

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