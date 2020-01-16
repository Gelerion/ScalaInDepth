package programming.in.scala.advanced.cats.functional.case_study.data_validation.check

import cats.Semigroup
import cats.syntax.either._    // for asLeft and asRight
import cats.syntax.semigroup._ // for |+|

// Algebraic Data Type approach
sealed trait CheckADT[E, A] {
  def and(that: CheckADT[E, A]): CheckADT[E, A] = AndADT(this, that)

  def apply(value: A)(implicit s: Semigroup[E]): Either[E, A] =
    this match {
      case PureADT(func) =>
        func(value)

      case AndADT(left /*Check[E, A]*/, right) =>
        (left(value), right(value)) match {
          case (Left(e1),  Left(e2))  => (e1 |+| e2).asLeft
          case (Left(e),   Right(a))  => e.asLeft
          case (Right(a),  Left(e))   => e.asLeft
          case (Right(a1), Right(a2)) => value.asRight
        }
    }
}

final case class AndADT[E, A](left: CheckADT[E, A], right: CheckADT[E, A]) extends CheckADT[E, A]
final case class PureADT[E, A](func: A => Either[E, A]) extends CheckADT[E, A]