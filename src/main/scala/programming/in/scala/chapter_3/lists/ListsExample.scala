package programming.in.scala.chapter_3.lists

/**
  * Created by denis.shuvalov on 27/02/2018.
  */
object ListsExample extends App {

  //For an immutable sequence of objects that share the same type you can use Scala's List class. As with arrays,
  //a List[String] contains only strings. Scala's List, scala.List, differs from Java's java.util.List type in
  //that Scala Lists are always immutable (whereas Java Lists can be mutable). More generally, Scala's List is
  //designed to enable a functional style of programming

  val oneTwoThree = List(1, 2, 3)

  //new val named oneTwoThree, initialized with a new List[Int] with the integer elements 1, 2, and 3. Because Lists
  //are immutable, they behave a bit like Java strings: when you call a method on a list that might seem by its name
  //to imply the list will mutate, it instead creates and returns a new list with the new value. For example,
  //List has a method named `:::' for list concatenation. Here's how you use it:

  val oneTwo = List(1, 2)
  val threeFour = List(3, 4)
  val oneTwoThreeFour: List[Int] = oneTwo ::: threeFour
  println(oneTwo + " and " + threeFour + " were not mutated.")
  println("Thus, " + oneTwoThreeFour + " is a new list.")

  //Perhaps the most common operator you'll use with lists is `::', which is pronounced "cons." Cons prepends a
  //new element to the beginning of an existing list and returns the resulting list.
  //For example, if you run this script:
  val twoThree = List(2, 3)
  val oneTwoThreeV2 = 1 :: twoThree
  println(s"Prepend to list with right operand: $oneTwoThreeV2")

  //NOTE!
  //In the expression "1 :: twoThree", :: is a method of its right operand, the list, twoThree. You might suspect
  //there's something amiss with the associativity of the :: method, but it is actually a simple rule to remember:
  //If a method is used in operator notation, such as a * b, the method is invoked on the left operand, as in
  //a.*(b)—unless the method name ends in a colon. If the method name ends in a colon, the method is invoked on the
  //right operand. Therefore, in 1 :: twoThree, the :: method is invoked on twoThree, passing in 1, like this: twoThree.::(1)

  //Initializing new list
  val oneTwoThreeStringed = 1 :: 2 :: 3 :: Nil
  println(s"String together elements with the cons operator and Nil: $oneTwoThreeStringed")

  //WHY NOT APPEND TO LISTS?
  //Class List does offer an "append" operation—it's written :+ but this operation is rarely used,
  //because the time it takes to append to a list grows linearly with the size of the list, whereas
  //prepending with :: takes constant time.
  val threeFourAppend = List(3, 4)
  val threeFourOne = threeFourAppend :+ 1
  val appendToNilList: List[Int] = Nil :+ 1 :+ 2 :+ 3
  println(s"Appending to list $threeFourOne")
  println(s"Appending to Nil $appendToNilList")

  //If you want to build a list efficiently by appending elements, you can prepend them and when you're done call
  //reverse. Or you can use a ListBuffer, a mutable list that does offer an append operation, and when you're done call toList



}
