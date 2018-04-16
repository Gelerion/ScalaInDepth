package programming.in.scala.chapter_21.imlicitconversionts.to.an.expected.`type`

import programming.in.scala.chapter_6.Rational

/**
  * Created by denis.shuvalov on 15/04/2018.
  *
  * Implicit conversions also apply to the receiver of a method call, the object on which the method is invoked.
  * This kind of implicit conversion has two main uses. First, receiver conversions allow smoother integration of a
  * new class into an existing class hierarchy. And second, they support writing domain-specific languages (DSLs) within the language.
  */
object ConvertingTheReceiver extends App {
  /*
  To see how it works, suppose you write down obj.doIt, and obj does not have a member named doIt. The compiler will
  try to insert conversions before giving up. In this case, the conversion needs to apply to the receiver, obj.
  The compiler will act as if the expected "type" of obj was "has a member named doIt." This "has a doIt" type is not a
  normal Scala type, but it is there conceptually and is why the compiler will insert an implicit conversion in this case.
   */

  val oneHalf = new Rational(1, 2)
  oneHalf * 1 //fine

  //1 * oneHalf overloaded method value + with alternatives (Double)Double <and> ... cannot be applied
  implicit def intToRational(x: Int) = new Rational(x, 1)
  1 + oneHalf

  //What happens behind the scenes here is that the Scala compiler first tries to type check the expression 1 + oneHalf
  //as it is. This fails because Int has several + methods, but none that takes a Rational argument. Next, the compiler
  //searches for an implicit conversion from Int to another type that has a + method which can be applied to a Rational.
  //It finds your conversion and applies it, which yields:
  intToRational(1) + oneHalf
}
