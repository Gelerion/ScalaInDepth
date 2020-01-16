package programming.in.scala.advanced.cats.functional.case_study.data_validation

import cats.syntax.either._
import programming.in.scala.advanced.cats.functional.case_study.data_validation.check.{CheckF, PureADT}
import programming.in.scala.advanced.cats.functional.case_study.data_validation.check.CheckADT
import programming.in.scala.advanced.cats.functional.case_study.data_validation.check_v2.{Check, Predicate}
import cats.data.{NonEmptyList, Validated}
import cats.syntax.apply._
import cats.syntax.validated._ // for valid and invalid

final case class User(username: String, email: String)

object DataValidation {

  type Errors = NonEmptyList[String]

  def main(args: Array[String]): Unit = {
    println(createUser("Noel", "noel@underscore.io")) //Valid(User(Noel,noel@underscore.io))
    println(createUser("", "dave@underscore@io")) //Invalid(NonEmptyList(Must be longer than 3 characters, Must contain a single @ character))
    //testCheckFMethod()
  }

  def createUser(username: String, email: String): Validated[Errors, User] =
    (checkUsername(username), checkEmail(email)).mapN(User)

  def error(s: String): NonEmptyList[String] = NonEmptyList(s, Nil)

  def longerThan(n: Int): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be longer than $n characters"),
      str => str.length > n)

  val alphanumeric: Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must be all alphanumeric characters"),
      str => str.forall(_.isLetterOrDigit))

  def contains(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char"),
      str => str.contains(char))

  def containsOnce(char: Char): Predicate[Errors, String] =
    Predicate.lift(
      error(s"Must contain the character $char only once"),
      str => str.count(c => c == char) == 1)

  // A username must contain at least four characters
  // and consist entirely of alphanumeric characters
  val checkUsername: Check[Errors, String, String] = Check(longerThan(3) and alphanumeric)

  // An email address must contain a single `@` sign.
  // Split the string at the `@`.
  // The string to the left must not be empty.
  // The string to the right must be
  // at least three characters long and contain a dot.
  val splitEmail: Check[Errors, String, (String, String)] =
  Check(_.split('@') match {
    case Array(name, domain) =>
      (name, domain).validNel[String] //nel - non empty list

    case other =>
      "Must contain a single @ character".invalidNel[(String, String)]
  })

  val checkLeft: Check[Errors, String, String] =
    Check(longerThan(0))

  val checkRight: Check[Errors, String, String] =
    Check(longerThan(3) and contains('.'))

  val joinEmail: Check[Errors, (String, String), String] =
    Check { case (l, r) =>
      (checkLeft(l), checkRight(r)).mapN(_ + "@" + _)
    }

  val checkEmail: Check[Errors, String, String] =
    splitEmail andThen joinEmail


  //-------------------------------------------

  private def testCheckFMethod(): Unit = {
    import cats.instances.list._ // for Semigroup
    val a: CheckF[List[String], Int] =
      CheckF { v =>
        if (v > 2) v.asRight
        else List("Must be > 2").asLeft
      }

    val b: CheckF[List[String], Int] =
      CheckF { v =>
        if (v < -2) v.asRight
        else List("Must be < -2").asLeft
      }

    val check: CheckF[List[String], Int] = a and b

    println("check(5) = " + check(5)) //Left(List(Must be < -2))
    println("check(0) = " + check(0)) //Left(List(Must be > 2, Must be < -2))

    val c: CheckADT[List[String], Int] = PureADT { v =>
      if (v > 2) v.asRight
      else List("Must be > 2").asLeft
    }

    val d: CheckADT[List[String], Int] = PureADT { v =>
        if(v < -2) v.asRight
        else List("Must be < -2").asLeft
      }

    val checkADT: CheckADT[List[String], Int] = c and d

    println("checkADT(5) = " + checkADT(5)) //Left(List(Must be < -2))
    println("checkADT(0) = " + checkADT(0)) //Left(List(Must be > 2, Must be < -2))
  }

}