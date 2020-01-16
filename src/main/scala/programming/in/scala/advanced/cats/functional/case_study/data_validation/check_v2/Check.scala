package programming.in.scala.advanced.cats.functional.case_study.data_validation.check_v2

import cats.Semigroup
import cats.data.Validated

//E - error container
//A - original value
//B - transformation type
sealed trait Check[E, A, B] {
  import Check._

  def apply(in: A)(implicit s: Semigroup[E]): Validated[E, B]

  def map[C](f: B => C): Check[E, A, C] = Map[E, A, B, C](this, f)

  def flatMap[C](f: B => Check[E, A, C]): Check[E, A, C] = FlatMap[E, A, B, C](this, f)

  def andThen[C](next: Check[E, B, C]): Check[E, A, C] = AndThen[E, A, B, C](this, next)
}

//Here is a complete implementation of Check.
//Due to a type inference bug in Scala’s pattern matching, we’ve switched to implementing apply using inheritance:
object Check {
  final case class Map[E, A, B, C](check: Check[E, A, B], func: B => C) extends Check[E, A, C] {
    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] = check(a) map func
  }

  final case class FlatMap[E, A, B, C](check: Check[E, A, B], func: B => Check[E, A, C]) extends Check[E, A, C] {
    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] = check(a).withEither(_.flatMap(b => func(b)(a).toEither))
  }

  final case class AndThen[E, A, B, C](check: Check[E, A, B], next: Check[E, B, C]) extends Check[E, A, C] {
    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] = check(a).withEither(_.flatMap(b => next(b).toEither))
  }

  final case class Pure[E, A, B](func: A => Validated[E, B]) extends Check[E, A, B] {
    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, B] = func(a)
  }

  final case class PurePredicate[E, A](pred: Predicate[E, A]) extends Check[E, A, A] {
    def apply(a: A)(implicit s: Semigroup[E]): Validated[E, A] = pred(a)
  }

  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] = PurePredicate(pred)

  def apply[E, A, B](func: A => Validated[E, B]): Check[E, A, B] = Pure(func)
}

//E - error container
//A - original value
//B - transformation type
/*sealed trait Check[E, A, B] {
  def apply(value: A)(implicit s: Semigroup[E]): Validated[E, B]

  def map[C](func: B => C): Check[E, A, C] = Map(this, func)

  def flatMap[C](func: B => Check[E, A, C]): Check[E, A, C] = FlatMap(this, func)

  //def andThen[A](g: R => A): T1 => A = { x => g(apply(x))
  def andThen[C](next: Check[E, B, C]): Check[E, A, C] = AndThen(this, next)
}

object Check {
  def apply[E, A](pred: Predicate[E, A]): Check[E, A, A] = Pure(pred)
}

//check[E, A, B] => check[E, A, C]
final case class Map[E, A, B, C](check: Check[E, A, B], func: B => C) extends Check[E, A, C] {
  def apply(in: A)(implicit s: Semigroup[E]): Validated[E, C] = check(in).map(func)
}

final case class Pure[E, A](pred: Predicate[E, A]) extends Check[E, A, A] {
  def apply(in: A)(implicit s: Semigroup[E]): Validated[E, A] = pred(in)
}

final case class FlatMap[E, A, B, C](check: Check[E, A, B], func: B => Check[E, A, C]) extends Check[E, A, C] {
  //It’s the same implementation strategy as before with one wrinkle: Validated doesn’t have a flatMap method.
  //To implement flatMap we must momentarily switch to Either and then switch back to Validated.
  //The withEither method on Validated does exactly this
  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
    check(a).withEither(_.flatMap(b => func(b)(a).toEither))
}

final case class AndThen[E, A, B, C](check1: Check[E, A, B], check2: Check[E, B, C]) extends Check[E, A, C] {
  def apply(a: A)(implicit s: Semigroup[E]): Validated[E, C] =
    check1(a).withEither(_.flatMap(b => check2(b).toEither))
}*/
