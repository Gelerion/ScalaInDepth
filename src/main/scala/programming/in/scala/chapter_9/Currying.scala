package programming.in.scala.chapter_9

/**
  * Created by denis.shuvalov on 11/03/2018.
  */
object Currying extends App {

  def plainOldSum(x: Int, y: Int) = x + y

  //apply this function to to two lists of one Int param each
  def curriedSum(x: Int)(y: Int) = x + y //(x: Int)(y: Int)Int
  println(curriedSum(1)(2))
  //println(curriedSum(1)) //missing argument error

  //What's happening here is that when you invoke curriedSum, you actually get two traditional function
  //invocations back to back. The first function invocation takes a single Int parameter named x, and returns a
  //function value for the second function. This second function takes the Int parameter y.

  def first(x: Int) = (y: Int) => x + y //(x: Int)Int => Int
  val second = first(1) //Int => Int = <function1>
  println(second(2))

  //These first and second functions are just an illustration of the currying process. They are not directly connected
  //to the curriedSum function. Nevertheless, there is a way to get an actual reference to curriedSum's "second" function.
  val onePlus = curriedSum(1)_ //Int => Int = <function1>
  //The underscore in curriedSum(1)_ is a placeholder for the second parameter list.

  //The result is a reference to a function that, when invoked, adds one to its sole Int argument and returns the result:
  println(onePlus(2))




}
