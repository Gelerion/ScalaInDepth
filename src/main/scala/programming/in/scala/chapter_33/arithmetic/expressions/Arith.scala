package programming.in.scala.chapter_33.arithmetic.expressions

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by denis.shuvalov on 14/05/2018.
  *
  * The parsers for arithmetic expressions are contained in a class that inherits from the trait JavaTokenParsers.
  * This trait provides the basic machinery for writing a parser and also provides some primitive parsers that
  * recognize some word classes: identifiers, string literals and numbers.
  */
class Arith extends JavaTokenParsers {

  // RegexParsers.literal("(")

  def expr: Parser[Any]   = term~rep("+"~term | "-"~term)
  def term: Parser[Any]   = factor~rep("*"~factor | "/"~factor)
  def factor: Parser[Any] = floatingPointNumber | "("~expr~")"

  //1. Every production becomes a method, so you need to prefix it with def.
  //2. The result type of each method is Parser[Any], so you need to change the ::= symbol to ": Parser[Any] ="
  //3. In the grammar, sequential composition was implicit, but in the program it is expressed by an explicit operator: ~.
  //   So you need to insert a ~ between every two consecutive symbols of a production.
  //4. Repetition is expressed rep( ... ) instead of \{ ... \}. Analogously (though not shown in the example),
  //   option is expressed opt( ... ) instead of [ ... ].
  //5. The period (.) at the end of each production is omitted—you can, however, write a semicolon (;) if you prefer.


}

object ParserExpr extends Arith {
  def main(args: Array[String]): Unit = {
    val input = "2 * (3 + 7)"
    println("input : " + input)
    println(parseAll(expr, input))
  }
}