package programming.in.scala.chapter_6

/**
  * Created by denis.shuvalov on 06/03/2018.
  */
object RationalExample extends App {

  val x = new Rational(1, 2)
  val y = new Rational(2, 3)

  println(s"$x + $y = ${x + y}")

  //Another thing to note is that given Scala's rules for operator precedence,
  //the * method will bind more tightly than the + method for Rationals
  //In other words, expressions involving + and * operations on Rationals will behave as expected.
  //For example, x + x * y will execute as x + (x * y), not (x + x) * y

  println(s"$x + $x * $y = ${x + x * y}")
  println(s"($x + $x) * $y = ${(x + x) * y}")

  //overloaded methods:
  println(s"$x * 5 = ${x * 5}")

  //IMPLICIT CONVERSIONS
  //Now that you can write r * 2, you might also want to swap the operands, as in 2 * r
  implicit def intToRational(x: Int) = new Rational(x)

  //For an implicit conversion to work, it needs to be in scope.
  println(s"5 * $x = ${5 * x}")
}
