package programming.in.scala.chapter_3.arrays

/**
  * Created by denis.shuvalov on 26/02/2018.
  */
object ParameterizeArraysWithTypes extends App {

  //Arrays are simply instances of classes like any other class in Scala.
  val greetings = new Array[String](3)

  //When an assignment is made to a variable to which parentheses and one or more arguments have been applied, the
  //compiler will transform that into an invocation of an update method that takes the arguments in parentheses as
  //well as the object to the right of the equals sign.
  greetings.update(0, "Hello")
  greetings(1) = ", " // not greetStrings[0]
  greetings(2) = "world!\n"

  println("------------------ for comprehension ------------------")
  for (greet <- greetings) print(greet)

  println("------------------ for with range ------------------")
  //if a method takes only one parameter, you can call it without a dot or parentheses.
  for(index <- 0 to 2) print(greetings(index))

  //When you apply parentheses surrounding one or more values to a variable,
  //Scala will transform the code into an invocation of a method named apply on that variable.
  val value = greetings.apply(0) //Thus accessing an element of an array in Scala is simply a method call like any other.


/*  val greetStrings = new Array[String](3)

  greetStrings.update(0, "Hello")
  greetStrings.update(1, ", ")
  greetStrings.update(2, "world!\n")

  for (i <- 0.to(2))
    print(greetStrings.apply(i))*/

  println("------------------ other way Array creation ------------------")
  //The compiler infers the type of the array to be Array[String]
  val numNames = Array("zero", "one", "two")
  //This apply method takes a variable number of arguments[2] and is defined on the Array companion object
  //you can think of this as calling a static method named apply on class Array
  val numNames2 = Array.apply("zero", "one", "two")


}
