package programming.in.scala.chapter_3.operators

/**
  * Created by denis.shuvalov on 27/02/2018.
  */
object OperatorsExample extends App {

  //0 to 2 is same as (0).to(2)
  private val range: Range.Inclusive = (0).to(2)
  println(range)

  println("------------------------------------")
  //Note that this syntax only works if you explicitly specify the receiver of the method call
  //You cannot write "println 10", but you can write "Console println 10"
  Console println "Pointless syntax here!"

  println("------------------------------------")
  //Scala doesn't technically have operator overloading, because it doesn't actually have operators in
  //the traditional sense. Instead, characters such as +, -, *, and / can be used in method names.
  //Thus, when you typed 1 + 2 into the Scala interpreter in Step 1, you were actually invoking a method
  //named + on the Int object 1, passing in 2 as a parameter.

  //int object with value 1
  //invoking on 1 a method named +
  //passing the Int object 2 to the '+' method
  val res: Int = 1 + 2
  println("1 + 2 = " + res)
  println("(1).+(2) = " + (1).+(2))

}
