package programming.in.scala.chapter_15.pattern.matching

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
class VariableBinding {

  //In addition to the standalone variable patterns, you can also add a variable to any other pattern.
  //You simply write the variable name, an at sign (@), and then the pattern

  //This gives you a variable-binding pattern, which means the pattern is to perform the pattern match as normal,
  //and if the pattern succeeds, set the variable to the matched object just as with a simple variable pattern.

//  expr match {
//    case UnOp("abs", e @ UnOp("abs", _)) => e
//    case _ =>
//  }
  //variable-binding pattern with e as the variable and UnOp("abs", _) as the pattern. If the entire pattern match
  //succeeds, then the portion that matched the UnOp("abs", _) part is made available as variable e. The result of
  //the case is just e, because e has the same value as expr but with one less absolute value operation.

  //case List(1, _*) => "a list beginning with 1, having any number of elements"
  //As demonstrated, this lets you match a List whose first element is 1, but so far, the List

  //hasn’t been accessed on the right side of the expression. When accessing a `List, you know that you can do this:
  //case list: List[_] => s"thanks for the List: $list"

  //so it seems like you should try this with a sequence pattern:
  //case list: List(1, _*) => s"thanks for the List: $list"
  //Unfortunately, this fails with the following compiler error

  // variable-binding pattern
  //case list @ List(1, _*) => s"$list"

  def matchType(x: Any): String = x match {
    //case x: List(1, _*) => s"$x"          // doesn't compile
    case x @ List(1, _*) => s"$x"           // works; prints the list
    //case Some(_) => "got a Some"          // works, but can't access the Some
    //case Some(x) => s"$x"                 // works, returns "foo"
    case x @ Some(_) => s"$x"               // works, returns "Some(foo)"
    case p @ Person(first, "Doe") => s"$p"  // works, returns "Person(John,Doe)"
  }
  println(matchType(List(1,2,3)))             // prints "List(1, 2, 3)"
  println(matchType(Some("foo")))             // prints "Some(foo)"
  println(matchType(Person("John", "Doe")))   // prints "Person(John,Doe)"
}

case class Person(firstName: String, lastName: String)