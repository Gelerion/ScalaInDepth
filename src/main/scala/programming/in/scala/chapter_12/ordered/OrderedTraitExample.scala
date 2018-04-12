package programming.in.scala.chapter_12.ordered

/**
  * Created by denis.shuvalov on 14/03/2018.
  */
class OrderedTraitExample {

}

//Suppose you took the Rational class and added comparison operations to it. You would end up with something like this:
/*

  class Rational(n: Int, d: Int) {
  // ...
  def < (that: Rational) =
    this.numer * that.denom < that.numer * this.denom
  def > (that: Rational) = that < this
  def <= (that: Rational) = (this < that) || (this == that)
  def >= (that: Rational) = (this > that) || (this == that)

}*/

//This class defines four comparison operators (<, >, <=, and >=), and it's a classic demonstration of the costs of
//defining a rich interface. First, notice that three of the comparison operators are defined in terms of the first
//one. For example, > is defined as the reverse of <, and <= is defined as literally "less than or equal." Next,
//notice that all three of these methods would be the same for any other class that is comparable. There is nothing
//special about rational numbers regarding <=. In a comparison context, <= is always used to mean "less than or equals."
//Overall, there is quite a lot of boilerplate code in this class which would be the same in any other class that
//implements comparison operations.

class Rational(n: Int, d: Int) extends Ordered[Rational] {
  val num = n
  val denom = d

   def compare(that: Rational): Int = {
     (this.num * that.denom) - (that.num * this.denom)
   }
}

object OrderedExample extends App {
  private val half = new Rational(1, 2)
  private val third = new Rational(2, 3)

  println(half < third) //true
  println(half > third) //false
}