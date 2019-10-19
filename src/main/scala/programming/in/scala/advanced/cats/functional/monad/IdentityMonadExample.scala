package programming.in.scala.advanced.cats.functional.monad

import cats.Monad
import cats.instances.option._
import cats.instances.list._
import cats.syntax.applicative._
import cats.syntax.functor._
import cats.syntax.flatMap._

import scala.language.higherKinds

object IdentityMonadExample {

  def main(args: Array[String]): Unit = {
    import cats.Id

    //The only restriction we’ve seen to this is that Scala cannot unify types and type constructors when
    //searching for implicits. Hence our need to re-type Int as Id[Int]
    val a = 3 : Id[Int]
    println("Id monad, sumSquare:")
    println(sumSquareV2(a, 4: Id[Int]))
    //What’s going on? Here is the definition of Id to explain:
    /*
    type Id[A] = A

    Id is actually a type alias that turns an atomic type into a single-parameter type constructor.
    We can cast any value of any type to a corresponding Id

    List(1, 2, 3) : Id[List[Int]]
    "Dave" : Id[String]
     */

    //Cats provides instances of various type classes for Id, including Functor and Monad.
    //These let us call map, flatMap, and pure passing in plain values:
    val three: Id[Int] = Monad[Id].pure(3)
    val four = Monad[Id].flatMap(three)(_ + 1)

    println("Id monad, flatMap:")
    println(four)


    import programming.in.scala.advanced.cats.functional.monad.FuncIdentity.FuncId
    val myTen: FuncId[Int] = 10 : FuncId[Int]
    println("My Id monad, map:")
    println(myTen.map(_ + 5))
  }

  def sumSquareV2[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y
}


object FuncIdentity {
  type FuncId[A] = A

  //Implement pure, map, and flatMap for Id
  implicit val identityMonad: FuncMonad[FuncId] = new FuncMonad[FuncId] {
    def pure[A](value: A): FuncId[A] = value

    def flatMap[A, B](initial: FuncId[A])(func: A => FuncId[B]): FuncId[B] = {
      func(initial)
    }
  }

}
