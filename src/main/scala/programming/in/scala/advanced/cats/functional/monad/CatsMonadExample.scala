package programming.in.scala.advanced.cats.functional.monad

import cats.Monad
import cats.instances.option._ // for Monad
import cats.instances.list._   // for Monad

object CatsMonadExample {

  def main(args: Array[String]): Unit = {
    val opt1: Option[Int] = Monad[Option].pure(3)

    val opt2: Option[Int] = Monad[Option].flatMap(opt1)(a => Some(a + 2))

    val opt3: Option[Int] = Monad[Option].map(opt2)(a => 100 * a)
    println(opt3)

    val list1: List[Int] = Monad[List].pure(3)
    println(list1)

    //Default instances
    println(Monad[Option].flatMap(Option(1))(a => Option(a * 2)))
    println(Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10)))

    //Syntax
    import cats.instances.option._   // for Monad
    import cats.instances.list._     // for Monad
    import cats.syntax.applicative._ // for pure

    println(1.pure[Option]) //Option[Int] = Some(1)
    println(1.pure[List]) //List[Int] = List(1)

    println("Sum square =====================")

    println(sumSquare(Option(3), Option(4)))
    println(sumSquare(List(1, 2, 3), List(4, 5)))
    //This method works well on Options and Lists but we can’t call it passing in plain values
  }

  import cats.syntax.functor._ // for map
  import cats.syntax.flatMap._ // for flatMap
  import scala.language.higherKinds

  def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    a.flatMap(x => b.map(y => x*x + y*y))

  def sumSquareFor[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y


}
