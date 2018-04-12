package programming.in.scala.chapter_8.functional

import java.io.PrintStream

/**
  * Created by denis.shuvalov on 08/03/2018.
  */
object SpecialFunctions extends App {
  // ---------- Repeated parameters
  def echo(args: String*) = for (arg <- args) println(arg)
  //Defined this way, echo can be called with zero to many String arguments

  //the type of the repeated parameter is an Array of the declared type of the parameter. Thus, the type of args inside
  //the echo function, which is declared as type "String*" is actually Array[String]
  //Nevertheless, if you have an array of the appropriate type, and you attempt to pass it as a repeated parameter,
  //you'll get a compiler error:
  //    scala> val arr = Array("What's", "up", "doc?")
  //    arr: Array[String] = Array(What's, up, doc?)
  //    <console>:10: error: type mismatch;

  val arr = Array("What's", "up", "doc?")
  echo(arr: _*)
  println("-------------------------------------")

  // ---------- Named arguments
  def speed(distance: Float, time: Float): Float = distance / time
  speed(time = 10, distance = 100) // Float = 10.0

  // ---------- Default parameter values
  def printTime(out: PrintStream = Console.out, divisor: Int = 1) = out.println(s"time: ${System.currentTimeMillis()/divisor}")
  printTime(out = Console.err)


}
