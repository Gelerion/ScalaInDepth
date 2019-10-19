package programming.in.scala.advanced.cats.functional.monad

import scala.language.higherKinds

object MonadExample {

  def main(args: Array[String]): Unit = {

  }

}

trait FuncMonad[F[_]] {
  def pure[A](a: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(v => pure(func(v)))

}