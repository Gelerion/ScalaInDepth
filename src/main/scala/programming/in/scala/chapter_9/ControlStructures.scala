package programming.in.scala.chapter_9

import java.io.{File, PrintWriter}

/**
  * Created by denis.shuvalov on 11/03/2018.
  */
object ControlStructures extends App {
  //In languages with first-class functions, you can effectively make new control structures
  //even though the syntax of the language is fixed.


  //"twice" control structure, which repeats an operation two times and returns the result
  def twice(op: Double => Double, x: Double) = op(op(x))
  //twice: (op: Double => Double, x: Double)Double

  println(twice(_ + 1, 5)) //7

  //------------------------------------------------------------------------
  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    }
    finally {
      writer.close()
    }
  }

  withPrintWriter(
    new File("date.txt"),
    writer => writer.println(new java.util.Date)
  )

  //------------------------------------------------------------------------
  //One way in which you can make the client code look a bit more like a built-in control structure is to use curly
  //braces instead of parentheses to surround the argument list. In any method invocation in Scala in which you're
  //passing in exactly one argument, you can opt to use curly braces to surround the argument instead of parentheses.

  println("Hello, world!")
  println { "Hello, world!" }
  //in the second example, you used curly braces instead of parentheses to surround the arguments to println.
  //This curly braces technique will work, however, only if you're passing in one argument

  //------------------------------------------------------------------------
  //The purpose of this ability to substitute curly braces for parentheses for passing in one argument is to enable
  //client programmers to write function literals between curly braces. This can make a method call feel more like a
  //control abstraction. Take the withPrintWriter method defined previously as an example. In its most recent form,
  //withPrintWriter takes two arguments, so you can't use curly braces. Nevertheless, because the function passed to
  //withPrintWriter is the last argument in the list, you can use currying to pull the first argument, the File,
  //into a separate argument list. This will leave the function as the lone parameter of the second argument list.

  def withPrintWriterV2(file: File)(op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  val file = new File(".")
  withPrintWriterV2(file) { writer =>
    writer.println(new java.util.Date)
  }


}
