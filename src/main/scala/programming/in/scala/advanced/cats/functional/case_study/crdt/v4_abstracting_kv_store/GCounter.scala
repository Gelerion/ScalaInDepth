package programming.in.scala.advanced.cats.functional.case_study.crdt.v4_abstracting_kv_store

import cats.kernel.CommutativeMonoid
import programming.in.scala.advanced.cats.functional.case_study.crdt.v2_generalization.BoundedSemiLattice
import cats.syntax.semigroup._
import cats.syntax.foldable._  // for combineTll
import cats.instances.list._
import KeyValueStore.KvsOps //aka syntax

trait GCounter [F[_, _], K, V] {
  def increment(f: F[K, V])(k: K, v: V)(implicit m: CommutativeMonoid[V]): F[K, V]

  def merge(f1: F[K, V], f2: F[K, V])(implicit b: BoundedSemiLattice[V]): F[K, V]

  def total(f: F[K, V])(implicit m: CommutativeMonoid[V]): V
}

object GCounter {
  def apply[F[_, _], K, V](implicit counter: GCounter[F, K, V]): GCounter[F, K, V] = counter

  implicit def gcounterInstance[F[_, _], K, V]
  (implicit kvs: KeyValueStore[F], km: CommutativeMonoid[F[K, V]]): GCounter[F, K, V] = new GCounter[F, K, V] {

      def increment(store: F[K, V])(key: K, value: V)(implicit m: CommutativeMonoid[V]): F[K, V] = {
        val total = store.getOrElse(key, m.empty) |+| value
        store.put(key, total)
      }

      def merge(counter1: F[K, V], counter2: F[K, V])(implicit b: BoundedSemiLattice[V]): F[K, V] =
        counter1 |+| counter2

      def total(store: F[K, V])(implicit m: CommutativeMonoid[V]): V =
        store.values.combineAll
    }
}