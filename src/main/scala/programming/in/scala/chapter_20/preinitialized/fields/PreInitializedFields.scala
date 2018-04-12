package programming.in.scala.chapter_20.preinitialized.fields



/**
  * Created by denis.shuvalov on 10/04/2018.
  */
object PreInitializedFields extends App {
  //The first solution, pre-initialized fields, lets you initialize a field of a subclass before the superclass is called.
  //To do this, simply place the field definition in braces before the superclass constructor call.

  val x = 2
  val rat = new {
    val numerArg = 1 * x
    val denomArg = 2 * x
  } with RationalTrait

  println(s"${rat.numerArg}/${rat.denomArg}")

  // OR
  object twoThirds extends {
    val numerArg = 2
    val denomArg = 3
  } with RationalTrait

  //BUT
/*  new {
    val numerArg = 1
    val denomArg = this.numerArg * 2
  } with RationalTrait
  <console>:11: error: value numerArg is not a member of object*/

  //The example did not compile because the reference this.numerArg was looking for a numerArg field in the object
  //containing the new (which in this case was the synthetic object named $iw, into which the interpreter puts user input
  //lines). Once more, pre-initialized fields behave in this respect like class constructor arguments.

}

// OR
class RationalClass(n: Int, d: Int) extends {
  val numerArg = n
  val denomArg = d
} with RationalTrait {
  //Because pre-initialized fields are initialized before the superclass constructor is called, their initializers
  //cannot refer to the object that's being constructed. Consequently, if such an initializer refers to this, the
  //reference goes to the object containing the class or object that's being constructed, not the constructed object itself.
  def + (that: RationalClass) = new RationalClass(
    numer * that.denom + that.numer * denom,
    denom * that.denom
  )
}

trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
  require(denomArg != 0)
  private val g = gcd(numerArg, denomArg)
  val numer = numerArg / g
  val denom = denomArg / g
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  override def toString = numer + "/" + denom
}
