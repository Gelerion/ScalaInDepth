package programming.in.scala.advanced.cats.functional.trampoline.adt

import scala.annotation.tailrec


/*
Trampoline is the term for this technique, think about it as a program description.
The program description can be either:
 * Done if there are no computations to be done and we can yield a value
 * More if there is a recursive function call to be made
 */
sealed trait Trampoline[A] {
  //embedding run interpreter into Trampoline
  def resume: Either[() => Trampoline[A]/*More*/, A/*Done*/ ] =
    this match {
      case Done(value) => Right(value)
      case More(fn) => Left(fn)
    }

  @tailrec final def run: A = resume match {
    case Left(more) => more().run
    case Right(value) => value
  }
}

case class Done[A](value: A) extends Trampoline[A]
case class More[A](call: () => Trampoline[A]) extends Trampoline[A]