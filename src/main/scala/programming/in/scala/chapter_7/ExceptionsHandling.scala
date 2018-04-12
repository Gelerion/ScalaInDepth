package programming.in.scala.chapter_7

import java.net.{MalformedURLException, URL}

import scala.io.StdIn

/**
  * Created by denis.shuvalov on 07/03/2018.
  */
object ExceptionsHandling {

  //Technically, an exception throw has type Nothing. You can use a throw as an expression even though it will
  // never actually evaluate to anything. This little bit of technical gymnastics might sound weird, but is
  // frequently useful in cases like the previous example. One branch of an if computes a value, while the other
  // throws an exception and computes Nothing. The type of the whole if expression is then the type of that branch
  // which does compute something. Type Nothing
  val n = StdIn.readInt()
  val half = if (n % 2 == 0) n / 2 else throw new RuntimeException("n must be even")

  //try finally same as in Java
  //! in Scala you can employ a technique called the loan pattern to achieve the same goal more concisely.

  //As with most other Scala control structures, try-catch-finally results in a value.
  def urlFor(path: String) =
    try {
      new URL(path)
    } catch {
      case e: MalformedURLException => new URL("http://www.scala-lang.org")
    }

  //if a finally clause includes an explicit return statement, or throws an exception, that return value or exception
  //ill "overrule" any previous one that originated in the try block or one of its catch clauses.
  def f(): Int = try return 1 finally return 2
  def g(): Int = try 1 finally 2
}
