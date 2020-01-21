package programming.in.scala.advanced.cats.functional.case_study.crdt.v3_abstracting_type_classes

import cats.kernel.CommutativeMonoid
import programming.in.scala.advanced.cats.functional.case_study.crdt.v2_generalization.BoundedSemiLattice

import cats.syntax.semigroup._
import cats.syntax.foldable._  // for combineTll
import cats.instances.map._
import cats.instances.list._


trait GCounterV3[F[_, _], K, V] {
  def increment(f: F[K, V])(k: K, v: V)(implicit m: CommutativeMonoid[V]): F[K, V]

  def merge(f1: F[K, V], f2: F[K, V])(implicit b: BoundedSemiLattice[V]): F[K, V]

  def total(f: F[K, V])(implicit m: CommutativeMonoid[V]): V
}

object GCounterV3 {
  def apply[F[_, _], K, V](implicit counter: GCounterV3[F, K, V]): GCounterV3[F, K, V] = counter

  implicit def mapStoreGCounter[K, V]: GCounterV3[Map, K, V] = new GCounterV3[Map, K, V] {
    def increment(map: Map[K, V])(k: K, v: V)(implicit m: CommutativeMonoid[V]): Map[K, V] = {
      val combined = v |+| map.getOrElse(k, m.empty)
      map + (k -> combined)
    }

    def merge(map1: Map[K, V], map2: Map[K, V])(implicit b: BoundedSemiLattice[V]): Map[K, V] = map1 |+| map2 //instances.map

    def total(map: Map[K, V])(implicit m: CommutativeMonoid[V]): V = map.values.toList.combineAll //instances.list
  }
}
