package programming.in.scala.chapter_11

/**
  * Created by denis.shuvalov on 13/03/2018.
  */
object NothingExample {

  //For instance there's the error method in the Predef object of Scala's standard library, which is defined like this:
  def error(message: String): Nothing = throw new RuntimeException(message)

  //The return type of error is Nothing, which tells users that the method will not return normally (it throws an
  //exception instead). Because Nothing is a subtype of every other type, you can use methods like error in
  //very flexible ways. For instance:
  def divide(x: Int, y: Int): Int =
    if (y != 0) x / y
    else error("can't divide by zero")

}
