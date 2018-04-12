package programming.in.scala.chapter_3.functional.style

/**
  * Created by denis.shuvalov on 28/02/2018.
  */
object MoreFunctionalRefactoring extends App {

//  The first step is to recognize the difference between the two styles in code. One telltale sign is that
//  if code contains any vars, it is probably in an imperative style. If the code contains no vars at all—i.e.,
//  it contains only vals—it is probably in a functional style. One way to move towards a functional style,
//  therefore, is to try to program without vars.

  val args1 = Array("zero", "one", "two")
  printArgsImperative(args1)
  printArgsPartiallyFunctional(args1)
  println(formatArgsFunctional(args1))

  // A function without side effects or vars
  //Now you're really functional: no side effects or vars in sight. The mkString method, which you can call on any
  //iterable collection (including arrays, lists, sets, and maps), returns a string consisting of the result of
  //calling toString on each element, separated by the passed string.
  // def formatArgs(args: Array[String]) = args.mkString("\n")
  def formatArgsFunctional(args: Array[String]) = {
    println("Functional:")
    println("-------------------------")
    args.mkString("\n")
  }

  //The refactored printArgs method is not purely functional because it has side effects—in this case, its
  //side effect is printing to the standard output stream. The telltale sign of a function with side effects
  //is that its result type is Unit. If a function isn't returning any interesting value, which is what a result
  //type of Unit means, the only way that function can make a difference in the world is through some kind of side effect.
  def printArgsPartiallyFunctional(args: Array[String]): Unit = {
    println("Partially Functional:")
    println("-------------------------")
    args.foreach(println)
  }

  def printArgsImperative(args: Array[String]): Unit = {
    println("Imperative:")
    println("-------------------------")
    var i = 0
    while (i < args.length) {
      println(args(i))
      i += 1
    }
  }

}
