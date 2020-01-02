package programming.in.scala.advanced.cats.functional.monoid_and_semigroup

import programming.in.scala.advanced.cats.functional.monoid_and_semigroup.MonoidOnBoolean.andMonoid

object MonoidOnSets {
 //What monoids and semigroups are there for sets?
 def main(args: Array[String]): Unit = {
   val setA: Set[Int] = Set(1, 2)
   val setB: Set[Int] = Set(2, 3)
   val setC: Set[Int] = Set(3, 4)

   println("UNION Set associativeLaw: " + MonoidLaws.associativeLaw[Set[Int]](setA, setB, setC)(unionSets))
   println("UNION Set identityLaw: " + MonoidLaws.identityLaw[Set[Int]](setA)(unionSets))

   val intSetMonoid = FuncMonoid[Set[Int]](unionSets)
   val strSetMonoid = FuncMonoid[Set[String]](symDiffMonoid)

   println("UNION two sets monoid: " + intSetMonoid.combine(setA, setB)) //Set(1, 2, 3)
 }

  //Set union forms a monoid along with the empty set:
  implicit def unionSets[A]: FuncMonoid[Set[A]] = new FuncMonoid[Set[A]] {
    override def combine(x: Set[A], y: Set[A]): Set[A] = x union y
    override def empty: Set[A] = Set.empty[A]
  }

  //Set intersection forms a semigroup, but doesn’t form a monoid because it has no identity element:
  implicit def intersectionSemigroupSets[A]: FuncSemigroup[Set[A]] = new FuncSemigroup[Set[A]] {
    override def combine(x: Set[A], y: Set[A]): Set[A] = x intersect y
  }

  //Set complement and set difference are not associative, so they cannot be considered for either monoids
  //or semigroups. However, symmetric difference (the union less the intersection) does also form a monoid
  //with the empty set:
  implicit def symDiffMonoid[A]: FuncMonoid[Set[A]] = new FuncMonoid[Set[A]] {
    def combine(a: Set[A], b: Set[A]): Set[A] = (a diff b) union (b diff a)
    def empty: Set[A] = Set.empty
  }
}
