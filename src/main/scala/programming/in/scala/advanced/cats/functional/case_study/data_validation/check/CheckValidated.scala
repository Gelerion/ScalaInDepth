package programming.in.scala.advanced.cats.functional.case_study.data_validation.check

import cats.Semigroup
import cats.syntax.semigroup._
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}
import cats.syntax.apply._     // for mapN

// Algebraic Data Type approach

/*
The implementation of apply for And is using the pattern for applicative functors.
Either has an Applicative instance, but it doesn’t have the semantics we want.
It fails fast instead of accumulating errors.

If we want to accumulate errors Validated is a more appropriate abstraction.
As a bonus, we get more code reuse because we can lean on the applicative instance of
Validated in the implementation of apply.
 */

sealed trait CheckValidated[E, A] {
  def and(that: CheckValidated[E, A]): CheckValidated[E, A] = And(this, that)

  def or(that: CheckValidated[E, A]): CheckValidated[E, A] = Or(this, that)

  def apply(value: A)(implicit s: Semigroup[E]): Validated[E, A] =
    this match {
      case Pure(func) =>
        func(value)

      case And(left, right) =>
        (left(value), right(value)).mapN((_/*A*/, _) => value)

      case Or(left, right) =>
        left(value) match {
          case valid @ Valid(_) => valid
          case Invalid(firstError) =>
            right(value) match {
              case valid @ Valid(_) => valid
              case Invalid(secondError) => Invalid(firstError |+| secondError)
            }
        }
    }
}

final case class And[E, A](left: CheckValidated[E, A], right: CheckValidated[E, A]) extends CheckValidated[E, A]
final case class Or[E, A](left: CheckValidated[E, A], right: CheckValidated[E, A]) extends CheckValidated[E, A]
final case class Pure[E, A](func: A => Validated[E, A]) extends CheckValidated[E, A]

//Transforming Data
/*
One of our requirements is the ability to transform data. This allows us to support additional scenarios like parsing input.
The obvious starting point is map. When we try to implement this, we immediately run into a wall.
Our current definition of Check requires the input and output types to be the same:
  type Check[E, A] = A => Either[E, A]

When we map over a check, what type do we assign to the result? It can’t be A and it can’t be B. We are at an impasse:
  def map(check: Check[E, A])(func: A => B): Check[E, ???]

To implement map we need to change the definition of Check. Specifically, we need to a new type variable
to separate the input type from the output:
  type Check[E, A, B] = A => Either[E, B]

Checks can now represent operations like parsing a String as an Int:
  val parseInt: Check[List[String], String, Int] =

However, splitting our input and output types raises another issue. Up until now we have operated under the
assumption that a Check always returns its input when successful. We used this in and and or to ignore the
output of the left and right rules and simply return the original input on success
  (this(a), that(a)) match {
  case And(left, right) =>
    (left(a), right(a))
      .mapN((result1, result2) => Right(a))

In our new formulation we can’t return Right(a) because its type is Either[E, A] not Either[E, B].
We’re forced to make an arbitrary choice between returning Right(result1) and Right(result2).
The same is true of the or method. From this we can derive two things:
 - we should strive to make the laws we adhere to explicit; and
 - the code is telling us we have the wrong abstraction in Check.
 */














