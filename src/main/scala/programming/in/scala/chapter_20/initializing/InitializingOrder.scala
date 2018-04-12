package programming.in.scala.chapter_20.initializing

/**
  * Created by denis.shuvalov on 10/04/2018.
  */
class InitializingOrder {
  //Abstract vals sometimes play a role analogous to superclass parameters: they let you provide details in a subclass
  //that are missing in a superclass. This is particularly important for traits, because traits don't have a constructor
  //to which you could pass parameters. So the usual notion of parameterizing a trait works via abstract vals that
  //are implemented in subclasses.
}

//he RationalTrait trait given here defines instead two abstract vals: numerArg and denomArg.
//To instantiate a concrete instance of that trait, you need to implement the abstract val definitions.
trait RationalTrait {
  val numerArg: Int
  val denomArg: Int
}

object InitOrder extends App {
  //Here the keyword new appears in front of a trait name, RationalTrait, which is followed by a class body in curly
  //braces. This expression yields an instance of an anonymous class that mixes in the trait and is defined by the body
  val rational_1 = new RationalTrait {
    //the expressions, expr1 and expr2, are evaluated as part of the initialization of the anonymous class,
    //but the anonymous class is initialized after the RationalTrait.
    //So the values of numerArg and denomArg are not available during the initialization of RationalTrait
    val numerArg: Int = 1
    val denomArg: Int = 2
  }

  println(s"${rational_1.numerArg}/${rational_1.denomArg}")

  //This particular anonymous class instantiation has an effect analogous to the instance creation new Rational(1, 2)

  //!! The analogy is not perfect, however. There's a subtle difference concerning the order in which expressions are initialized.
  //When you write: new Rational(expr1, expr2)
  //the two expressions, expr1 and expr2, are evaluated before class Rational is initialized, so the values of expr1
  //and expr2 are available for the initialization of class Rational.'

/*  val x = 2
  new RationalTraitUsingArgs { //IllegalArgumentException: requirement failed
    val numerArg = 1 * x
    val denomArg = 2 * x
  }*/

  //This example demonstrates that initialization order is not the same for class parameters and abstract fields.
  //A class parameter argument is evaluated before it is passed to the class constructor (unless the parameter is by-name).
  //An implementing val definition in a subclass, by contrast, is evaluated only after the superclass has been initialized.
}

trait RationalTraitUsingArgs {
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