package programming.in.scala.advanced.cats.functional.case_study.data_validation.check

import cats.Semigroup
import cats.syntax.either._    // for asLeft and asRight
import cats.syntax.semigroup._ // for |+|

final case class CheckF[E, A](func: A => Either[E, A]) {

  def apply(value: A): Either[E, A] = func(value)

  def and(that: CheckF[E, A])(implicit s: Semigroup[E]): CheckF[E, A] = CheckF { in =>
    (this.apply(in), that.apply(in)) match {
      case (Left(e1), Left(e2))     =>  (e1 |+| e2).asLeft
      case (Left(error), Right(_))  => error.asLeft
      case (Right(_),  Left(error)) => error.asLeft
      case (Right(_), Right(_))     => in.asRight
    }
  }
}

/*
What happens if we try to create checks that fail with a type that we can’t accumulate? For example,
there is no Semigroup instance for Nothing. What happens if we create instances of CheckF[Nothing, A]?

val a: CheckF[Nothing, Int] =
  CheckF(v => v.asRight)

val b: CheckF[Nothing, Int] =
  CheckF(v => v.asRight)
We can create checks just fine but when we come to combine them we get an error we might expect:

val check = a and b
// <console>:31: error: could not find implicit value for parameter s: cats.Semigroup[Nothing]
//        val check = a and b
//
 */
