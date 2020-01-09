package programming.in.scala.advanced.cats.functional.foldable_and_traverse

import cats.Foldable
import cats.instances.list._ // for Foldable
import cats.instances.int._ // for Monoid
import cats.instances.string._ // for Monoid
import cats.syntax.foldable._ // for combineAll and foldMap

object CatsFoldable {

  /*
  Cats’ Foldable abstracts foldLeft and foldRight into a type class. Instances of Foldable define these
  two methods and inherit a host of derived methods. Cats provides out-of-the-box instances of
  Foldable for a handful of Scala data types: List, Vector, Stream, and Option.
   */
  def main(args: Array[String]): Unit = {
    val ints = List(1, 2, 3)

    Foldable[List].foldLeft(ints, 0)(_ + _)

    Foldable[List].find(List(1, 2, 3))(_ % 2 == 0) //Some(2)

    Foldable[List].combineAll(List(1, 2, 3))

    List(1, 2, 3).combineAll
    // res16: Int = 6

    List(1, 2, 3).foldMap(_.toString)
    // res17: String = 123
  }

  /*
  Using Eval means folding is always stack safe, even when the collection’s default definition of foldRight is not.
  For example, the default implementation of foldRight for Stream is not stack safe. The longer the stream,
  the larger the stack requirements for the fold. A sufficiently large stream will trigger a StackOverflowError:

  import cats.Eval
  import cats.Foldable

  def bigData = (1 to 100000).toStream

  bigData.foldRight(0L)(_ + _)
  // java.lang.StackOverflowError ...
   */

  import cats.Eval
  import cats.Foldable
  import cats.instances.stream._ // for Foldable


  def bigData: Stream[Int] = (1 to 100000).toStream
  val eval: Eval[Long] = Foldable[Stream].foldRight(bigData, Eval.now(0L)) { (num, eval) =>
    eval.map(_ + num)
  }
}
