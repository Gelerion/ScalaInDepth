package programming.in.scala.advanced.cats.functional.case_study.map_reduce

import cats.kernel.Monoid

import scala.concurrent.Await
//import cats.syntax.monoid
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import cats.syntax.semigroup._ // for |+|

object MapReduce {

  def main(args: Array[String]): Unit = {
    import cats.instances.int._ // for Monoid
    println("foldMap(Vector(1, 2, 3))(identity) = " + foldMap(Vector(1, 2, 3))(identity)) // 6
    println("parallelFoldMap(Vector(1, 2, 3))(identity) = " +  Await.result(parallelFoldMap(Vector(1, 2, 3))(identity), 1.second))

    import cats.instances.string._ // for Monoid
    println("foldMap(Vector(1, 2, 3))(_.toString + !) = " + foldMap(Vector(1, 2, 3))(_.toString + "! ")) //1! 2! 3!

  }

  /** Single-threaded map-reduce function.
    * Maps `func` over `values` and reduces using a `Monoid[B]`.
    */
  def foldMap[A, B: Monoid](values: Vector[A])(fn: A => B): B = {
    values.map(fn).foldLeft(Monoid[B].empty)(Monoid[B].combine)
    //import cats.syntax.semigroup._ // for |+|
    //values.foldLeft(Monoid[B].empty)(_ |+| func(_))
  }

  //My implementation
//  def parallelFoldMap[A, B : Monoid](values: Vector[A])(func: A => B): Future[B] = {
//    Future.foldLeft(values.grouped(Runtime.getRuntime.availableProcessors)
//      .map(group => Future(foldMap(group)(func))).toList)(Monoid[B].empty)(Monoid[B].combine)
//  }

  def parallelFoldMap[A, B : Monoid](values: Vector[A])(func: A => B): Future[B] = {
    // Calculate the number of items to pass to each CPU:
    val numCores  = Runtime.getRuntime.availableProcessors
    val groupSize = (1.0 * values.size / numCores).ceil.toInt

    // Create one group for each CPU:
    val groups: Iterator[Vector[A]] = values.grouped(groupSize)

    // Create a future to foldMap each group:
    val futures: Iterator[Future[B]] = groups.map(group => Future(foldMap(group)(func)))

    // foldMap over the groups to calculate a final result:
    Future.sequence(futures) map { iterable =>
      iterable.foldLeft(Monoid[B].empty)(_ |+| _)
    }
  }

  import cats.instances.future._ // for Applicative and Monad
  import cats.instances.vector._ // for Foldable and Traverse

  import cats.syntax.foldable._  // for combineAll and foldMap
  import cats.syntax.traverse._  // for traverse


  def parallelFoldMapFoldable[A, B : Monoid](values: Vector[A])(func: A => B): Future[B] = {
    val groupSize = (1.0 * values.size / Runtime.getRuntime.availableProcessors).ceil.toInt
    values.grouped(groupSize).toVector
      .traverse(group => Future(group.foldMap(func)))
      .map(_.combineAll)
  }
}
