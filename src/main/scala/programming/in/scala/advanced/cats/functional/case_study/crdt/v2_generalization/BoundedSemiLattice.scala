package programming.in.scala.advanced.cats.functional.case_study.crdt.v2_generalization

import cats.kernel.CommutativeMonoid

//idempotent commutative monoid, also called a bounded semi lattice
trait BoundedSemiLattice[A] extends CommutativeMonoid[A] {
  def combine(a1: A, a2: A): A
  def empty: A
}

object BoundedSemiLattice {
  //The instance for Int will technically only hold for non-negative numbers,
  //but you don’t need to model non-negativity explicitly in the types
  implicit val intBoundedSemiLattice: BoundedSemiLattice[Int] = new BoundedSemiLattice[Int] {
    def combine(a1: Int, a2: Int): Int = a1 max a2
    val empty: Int = 0
  }

  implicit def setInstance[A](): BoundedSemiLattice[Set[A]] = new BoundedSemiLattice[Set[A]]{
      def combine(a1: Set[A], a2: Set[A]): Set[A] = a1 union a2
      val empty: Set[A] = Set.empty[A]
    }
}