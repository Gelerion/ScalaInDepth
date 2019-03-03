package programming.in.scala.chapter_33.arithmetic.expressions

import scala.util.parsing.combinator.JavaTokenParsers

object ArithParserAndEvaluatorV2 extends JavaTokenParsers {

   //----------------------------------------------------
  //The chainl1 combinator

  /*
  Parsing a chain of elements separated with some other elements, which prescribe the functions to
  combine the elements of the first type, is a frequently occuring parsing task. Such functionality is
  required, for example, to implement operators in nearly all programming languages.

  In our case factors are separated with ’*’ and ’/’ that prescribe multiplication and
  division functions, respectively. Similarly, terms are separated with ’+’ and ’-’ that dictate addition
  and subtraction functions. We have seen that this can be implemented using ^^ combinator, yet the
  grammar definition becomes too verbose.
   */

  //------------------------
  //  def expr: Parser[Double] = term ~ rep("+" ~ term | "-" ~ term) ^^ {
  //    case number ~ list => (number /: list) {
  //      case (acc, "+" ~ nextNum) => acc + nextNum
  //      case (acc, "-" ~ nextNum) => acc - nextNum
  //    }
  //  }
  //------------------------
  // TO:
  //------------------------
  //  def expr: Parser[Double] = chainl1(term, "+" ^^^ Add | "-" ^^^ Sub)
  //------------------------

  //where term is the parser for main elements, Add and Sub are references to arithmetic functions that we
  //will define later,

  /*
  Formally, the chainl1 combinator takes two parsers as arguments: the first one with some parsing
  result of type “T”, and the second one whose parsing result is a function of type “(T,T) => T”. That
  function will be applied to the consecutive results returned by p, like a left fold. Of course, the function
  is applied only if p succeeded more than once, otherwise the result of a single p is returned. And p must
  succeed at least once, as is indicated by the ’1’ in the parser name. The ’l’
  means that the chain of ps will be concatenated from the left to the right, producing a left-associated grouping.
  And if we want a right-associated grouping, then there is the chainr1 combinator implemented analogous to
  the chainl1.
   */


  // The ^^^ combinator
  /*
  The difference to the ^^ combinator is that the last takes a function that converts the previous parsing
  result to some other value (using the the previous parsing result as its input argument). In contrast,
  the ^^^ combinator simply takes a value that directly replaces the previous parsing result, effectively
  discarding it. The ^^^ combinator is useful in situations where the p
   */

  //----------------------------
//  def term: Parser[Double]   = chainl1(factor, "*" ^^^ Mul | "/" ^^^ Div)
//  def factor: Parser[Double] = floatingPointNumber ^^ {_.toDouble} | "(" ~> expr <~ ")"


  // ! Alternative notation for chainl1 - *

  //------------------------
  //term * ("+" ^^^ Add | "-" ^^^ Sub).
  //------------------------

  // This notation has in our opinion some drawbacks:
  //  - The common meaning of “*” is “zero or more”. In this case it works as “one or more”, which can
  //    be misleading.
  //  - There is no similar notation for chainr1.
  //  - The names chainl1 / chainr1 express their meanings more explicitly.


  def main(args: Array[String]): Unit = {
    val input = "10.5 - 4*2"
    println("input : " + input)
//    println(parseAll(expr, input))
  }
}
