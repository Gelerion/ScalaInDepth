package programming.in.scala.chapter_21.imlicitconversionts.to.an.expected.`type`

/**
  * Created by denis.shuvalov on 15/04/2018.
  *
  * Implicit conversion to an expected type is the first place the compiler will use implicits. The rule is simple.
  * Whenever the compiler sees an X, but needs a Y, it will look for an implicit function that converts X to Y.
  */
object ImplicitConversionsToAnExpectedType extends App {

  //For example, normally a double cannot be used as an integer because it loses precision:
  //val i: Int = 3.5 //error: type mismatch;

  //However, you can define an implicit conversion to smooth this over:
  implicit def doubleToInt(x: Double) = x.toInt
  val i: Int = 3.5

  /*
  What happens here is that the compiler sees a Double, specifically 3.5, in a context where it requires an Int.
  So far, the compiler is looking at an ordinary type error. Before giving up, though, it searches for an implicit
  conversion from Double to Int. In this case, it finds one: doubleToInt, because doubleToInt is in scope as a single
  identifier. (Outside the interpreter, you might bring doubleToInt into scope via an import or possibly through inheritance.)
  The compiler then inserts a call to doubleToInt automatically. Behind the scenes, the code becomes:
   */
  val i2: Int = doubleToInt(3.5)
  //This is literally an implicit conversion. You did not explicitly ask for conversion. Instead, you marked doubleToInt
  //as an available implicit conversion by bringing it into scope as a single identifier, and then the compiler
  //automatically used it when it needed to convert from a Double to an Int.

}
