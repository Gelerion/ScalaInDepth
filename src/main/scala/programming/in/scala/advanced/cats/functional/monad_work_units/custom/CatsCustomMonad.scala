package programming.in.scala.advanced.cats.functional.monad_work_units.custom


import cats.Monad
import scala.annotation.tailrec

object CatsCustomMonad {

  val optionMonad = new Monad[Option] {
    override def pure[A](x: A): Option[A] = Some(x)

    override def flatMap[A, B](opt: Option[A])(fn: A => Option[B]): Option[B] = opt flatMap fn

    /*
    The tailRecM method is an optimisation used in Cats to limit the amount of stack space consumed by
    nested calls to flatMap. The technique comes from a 2015 paper by PureScript creator Phil Freeman.
    The method should recursively call itself until the result of fn returns a Right.

    If we can make tailRecM tail-recursive, Cats is able to guarantee stack safety in recursive situations such as folding over large lists.
    If we can’t make tailRecM tail-recursive, Cats cannot make these guarantees and extreme use cases may result in StackOverflowErrors.
    All of the built-in monads in Cats have tail-recursive implementations of tailRecM, although writing one
    for custom monads can be a challenge… as we shall see.
     */
    @tailrec
    override def tailRecM[A, B](a: A)(fn: A => Option[Either[A, B]]): Option[B] = {
      fn(a) match {
        case None => None
        case Some(Left(value)) => tailRecM(value)(fn)
        case Some(Right(value)) => Some(value)
      }
    }
  }

  def main(args: Array[String]): Unit = {

  }


}
