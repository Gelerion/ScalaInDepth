package programming.in.scala.advanced.cats.functional.foldable_and_traverse.excercise

import cats.Applicative
import cats.data.Validated
import cats.instances.list._ // for Monoid
import scala.language.higherKinds
import cats.syntax.applicative._ // for pure
import cats.syntax.apply._ // for mapN

object ValidateInput {
  type ErrorsOr[A] = Validated[List[String], A]

  def main(args: Array[String]): Unit = {
    val value: ErrorsOr[List[Int]] = process(List(2, 4, 6))
    println(value) //Valid(List(2, 4, 6))
    println(process(List(1, 2, 3))) //Invalid(List(1 is not even, 3 is not even))
  }

  def process(inputs: List[Int]): ErrorsOr[List[Int]] =
    listTraverse(inputs) { n =>
      if (n % 2 == 0) {
        Validated.valid(n)
      } else {
        Validated.invalid(List(s"$n is not even"))
      }
    }

  def listTraverse[F[_]: Applicative, A, B](list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
    }

}
