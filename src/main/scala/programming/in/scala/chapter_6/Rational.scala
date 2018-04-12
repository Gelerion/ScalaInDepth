package programming.in.scala.chapter_6

/**
  * Created by denis.shuvalov on 06/03/2018.
  */
class Rational(n: Int, d: Int) {
  //Scala compiler will compile any code you place in the class body, which isn't part of a field or a method definition, into the primary constructor.
  //----------------------------------------------
  //println("Created " + n + "/" + d)
  require(d != 0) //preconditions

  //normalize with greatest common divisor
  private val g = gcd(n.abs, d.abs) //will execute before the other two, because it appears first in the source.

  //To access the numerator and denominator on that, you'll need to make them into fields
  val numer: Int = n / g //not accessible from outside the object
  val denom: Int = d / g
  //----------------------------------------------

  def this(n: Int) = this(1, n) // auxiliary constructor

  override def toString = numer + "/" + denom

  def + (that: Rational): Rational = {
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  }

  def * (that: Rational): Rational = {
    new Rational(numer * that.numer, denom * that.denom)
  }

  //to write r * 2 instead r * new Rational(2)
  def * (i: Int): Rational = {
    new Rational(numer + i * denom , denom)
  }

  def - (that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )

  def - (i: Int): Rational = new Rational(numer - i * denom, denom)

  def lessThan(that: Rational) = this.numer * that.denom < that.numer * this.denom

  //greatest common divisor
  private def gcd(a: Int, b: Int): Int = {
    if(b == 0) a else gcd(b, a % b)
  }


/*
    BROKEN
   Although class parameters n and d are in scope in the code of your add method, you can only access their value on
   the object on which add was invoked. Thus, when you say n or d in add's implementation, the compiler is happy to
   provide you with the values for these class parameters. But it won't let you say that.n or that.d because that
   does not refer to the Rational object on which add was invoked

  def add(that: Relational): Relational = {
    new Relational(n * that.d + that.n * d, d * that.d)
  }
*/

}
