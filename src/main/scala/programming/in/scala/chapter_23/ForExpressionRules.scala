package programming.in.scala.chapter_23

import programming.in.scala.chapter_23.filtering.Person

/**
  * Created by denis.shuvalov on 17/04/2018.
  */
object ForExpressionRules extends App {
  val lara = Person("Lara", isMale = false)
  val bob = Person("Bob", isMale = true)
  val julie = Person("Julie", isMale = false, lara, bob)
  val lena = Person("Lena", isMale = false)
  val persons = List(lara, bob, julie, lena)

  //general form
  //  for ( seq ) yield expr

  //Here, seq is a sequence of generators, definitions, and filters, with semicolons between successive elements.
  for (p <- persons; n = p.name; if (n startsWith "To"))
    yield n
  //This for expression contains one generator, one definition, and one filter.
  for {
    p <- persons              // a generator
    n = p.name                // a definition
    if (n startsWith "To")    // a filter
  } yield n

  //A generator is of the form:
  //  pat <- expr
  //The expression expr typically returns a list, even though you will see later that this can be generalized.
  //The pattern pat gets matched one-by-one against all elements of that list. If the match succeeds, the
  //variables in the pattern get bound to the corresponding parts of the element

  //A definition is of the form:
  //  pat = expr
  //This definition binds the pattern pat to the value of expr, so it has the same effect as a val definition:
  //  val x = expr

  //A filter is of the form:
  //  if expr
  //Here, expr is an expression of type Boolean. The filter drops from the iteration all elements for which expr returns false.

  //Every for expression starts with a generator. If there are several generators in a for expression,
  //later generators vary more rapidly than earlier ones.
  for (x <- List(1, 2); y <- List("one", "two"))
    yield (x, y) //List((1,one), (1,two), (2,one), (2,two))
}

case class Person(name: String, isMale: Boolean, children: Person*)

