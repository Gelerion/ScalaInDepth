package programming.in.scala.chapter_15.arithmetic.expressions

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
object ArithmeticDslExample extends App {

  //The other noteworthy thing about the declarations is that each subclass has a case modifier.
  //Classes with such a modifier are called case classes. Using the modifier makes the Scala
  //compiler add some syntactic conveniences to your class.

  val v = Var("x") //factory method

  val op = BinOp("+", Number(1), v)
  println(op)

  //The second syntactic convenience is that all arguments in the parameter list of a case class
  //implicitly get a val prefix, so they are maintained as fields:
  v.name
  op.left

  //Third, the compiler adds "natural" implementations of methods toString, hashCode, and equals to your class.
  //They will print, hash, and compare a whole tree consisting of the class and (recursively) all its arguments.
  //Since == in Scala always delegates to equals, this means that elements of case classes are
  //always compared structurally:
  op.right == Var("x") //true

  //Finally, the compiler adds a copy method to your class for making modified copies. This method is useful for
  //making a new instance of the class that is the same as another one except that one or two attributes are different.
  val opCopy = op.copy(operator = "-")
  println(opCopy)

  //However, the biggest advantage of case classes is that they support pattern matching.
}
