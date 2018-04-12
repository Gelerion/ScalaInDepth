package programming.in.scala.chapter_8.recursion

/**
  * Created by denis.shuvalov on 08/03/2018.
  */
object TailRecursion extends App {

  //A function like this is often used in search problems, with appropriate implementations for isGoodEnough and improve.
  def approximate(guess: Double): Double =
    if (isGoodEnough(guess)) guess
    else approximate(improve(guess))

  def isGoodEnough(guess: Double): Boolean = ???

  def improve(guess: Double) = ???

  //----------------------------
  //If you want the approximate function to run faster, you might be tempted to write it with
  //a while loop to try and speed it up, like this:
  def approximateLoop(initialGuess: Double): Double = {
    var guess = initialGuess
    while (!isGoodEnough(guess))
      guess = improve(guess)
    guess
  }


  //---------------------------------------
  /*
  This might seem surprising because a recursive call looks much more "expansive" than a simple jump from the end
  of a loop to its beginning. However, in the case of approximate above, the Scala compiler is able to apply an
  important optimization. Note that the recursive call is the last thing that happens in the evaluation of function
  approximate's body. Functions like approximate, which call themselves as their last action, are called tail recursive.
  The Scala compiler detects tail recursion and replaces it with a jump back to the beginning of the function, after
  updating the function parameters with the new values.
   */

  //--------------------------
  //This is NOT tail recursive, because it performs an increment operation after the recursive call.
  def boom(x: Int): Int =
    if (x == 0) throw new Exception("boom!")
    else boom(x - 1) + 1

  // scala>  boom(3)
  //  java.lang.Exception: boom!
  //    at .boom(<console>:5)
  //    at .boom(<console>:6)

  //If you now modify boom so that it does become tail recursive:
  def bang(x: Int): Int =
    if (x == 0) throw new Exception("bang!")
    else bang(x - 1)

  //  scala> bang(5)
  //  java.lang.Exception: bang!
  //    at .bang(<console>:5)
  //    at .<init>(<console>:6

  //------------------------------------
  /*
   The use of tail recursion in Scala is fairly limited because the JVM instruction set makes implementing more advanced
   forms of tail recursion very difficult. Scala only optimizes directly recursive calls back to the same function making
   the call. If the recursion is indirect, as in the following example of two mutually recursive functions, no optimization is possible:
   */
  def isEven(x: Int): Boolean =
  if (x == 0) true else isOdd(x - 1)
  def isOdd(x: Int): Boolean =
    if (x == 0) false else isEven(x - 1)

  //You also won't get a tail-call optimization if the final call goes to a function value.
  //Consider for instance the following recursive code:
  val funValue = nestedFun _
  def nestedFun(x: Int) : Unit = {
    if (x != 0) { println(x); funValue(x - 1) }
  }
}
