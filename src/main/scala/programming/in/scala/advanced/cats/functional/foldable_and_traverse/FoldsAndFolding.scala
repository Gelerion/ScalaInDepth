package programming.in.scala.advanced.cats.functional.foldable_and_traverse

import cats.Monoid

object FoldsAndFolding {

  def main(args: Array[String]): Unit = {
    println(show(Nil))
    println(show(List(1, 2, 3))) //3 then 2 then 1 then nil

    List(1, 2, 3).foldLeft(List.empty[Int])((a, i) => i :: a)  //List(3, 2, 1)
    List(1, 2, 3).foldRight(List.empty[Int])((i, a) => i :: a) //List(1, 2, 3)

    import cats.instances.int._ // for Monoid
    sumWithMonoid(List(1, 2, 3))
  }

  /*
  The Foldable type class captures the foldLeft and foldRight methods we’re used to in sequences like
  Lists, Vectors, and Streams. Using Foldable, we can write generic folds that work with a variety of sequence types.
  We can also invent new sequences and plug them into our code. Foldable gives us great use cases for Monoids and the Eval monad.
   */

  def show[A](list: List[A]): String = list.foldLeft("nil")((accum, item) => s"$item then $accum")

  /*
  foldLeft and foldRight are very general methods. We can use them to implement many of the other high-level
  sequence operations we know. Prove this to yourself by implementing substitutes for
  List's map, flatMap, filter, and sum methods in terms of foldRight.
   */

  def map[A, B](list: List[A])(func: A => B): List[B] = {
    list.foldRight(List.empty[B])((i, acc) => func(i) :: acc)
  }

  def flatMap[A, B](list: List[A])(func: A => List[B]): List[B] = {
    list.foldRight(List.empty[B])((i, acc) => func(i) ::: acc)
  }

  def filter[A](list: List[A])(func: A => Boolean): List[A] = {
    list.foldRight(List.empty[A])((i, acc) => if(func(i)) i :: acc else acc)
  }

  def sumWithNumeric[A](list: List[A])(implicit numeric: Numeric[A]): A =
    list.foldRight(numeric.zero)(numeric.plus)

  //sing cats.Monoid
  def sumWithMonoid[A](list: List[A])(implicit monoid: Monoid[A]): A =
    list.foldRight(monoid.empty)(monoid.combine)

  import scala.language.higherKinds
  import cats.Foldable
  import cats.syntax.foldable._ // for combineAll and foldMap
  def sum[F[_]: Foldable](values: F[Int]): Int = values.foldLeft(0)(_ + _)
}
