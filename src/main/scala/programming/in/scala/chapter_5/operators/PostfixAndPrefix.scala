package programming.in.scala.chapter_5.operators

/**
  * Created by denis.shuvalov on 05/03/2018.
  */
object PostfixAndPrefix extends App {

  //ANY METHOD CAN BE AN OPERATOR
  //In Scala operators are not special language syntax; any method can be an operator. What makes a method an operator
  //is how you use it. When you write "s.indexOf('o')", indexOf is not an operator. But when you write "s indexOf 'o'",
  //indexOf is an operator, because you're using it in operator notation.

  //Infix
  // -  infix operator notation, which means the method to invoke sits between the object and the parameter (or parameters)
  //    you wish to pass to the method, as in "7 + 2"
  val s = "Hello, world!"
  s indexOf ('o', 5)

  //prefix and postfix
  // - In prefix notation, you put the method name before the object on which you are invoking the method (for example, the `-' in -7)
  // - In postfix notation, you put the method after the object (for example, the "toLong" in "7 toLong").
  //prefix and postfix operators are unary: they take just one operand.

  //Prefix
  var d = -2.0
  var d1 = (2.0).unary_-
  //The only identifiers that can be used as prefix operators are +, -, !, and ~.

  //Postfix
  //Postfix operators are methods that take no arguments, when they are invoked without a dot or parentheses.
  s toLowerCase

  val neg = 2 + -3 //-2

  //When multiple operators of the same precedence appear side by side in an expression, the associativity
  //of the operators determines the way operators are grouped
  //So a * b yields a.*(b), but a ::: b yields b.:::(a)
  // a ::: b is more precisely treated as the following block:
  // { val x = a; b.:::(x) }
  //In this block a is still evaluated before b, and then the result of this evaluation is passed as an operand to b's ::: method

  //This associativity rule also plays a role when multiple operators of the same precedence appear side by side:
  //a ::: b ::: c is treated as a ::: (b ::: c). But a * b * c, by contrast, is treated as (a * b) * c


}
