package programming.in.scala.chapter_33.arithmetic.expressions

import scala.util.parsing.combinator.JavaTokenParsers

object ArithParserAndEvaluator extends JavaTokenParsers {

  def expr: Parser[Double] = term ~ rep("+" ~ term | "-" ~ term) ^^ {
    case number ~ list => (number /: list) {
      case (acc, "+" ~ nextNum) => acc + nextNum
      case (acc, "-" ~ nextNum) => acc - nextNum
    }
  }

  /*
  Using ^^ combinator, a multiplication of two numbers can be immediately replaced with the product
  of those numbers. Here is the simplified term rule:
     factor ~ "*" ~ factor ^^ {case t1 ~ _ ~ t2 => t1 * t2}.

  The term returns a sequence of two elements: a result of the factor parser, and a result of the rep parser.
  The first one should be already a number (we rewrote the result of factor from String to Double in
  the previous step), the second is a List (each rep parser returns a List).

  Now lets look closer at the list. Its elements are again sequences that consist of two elements: a String
  and a number. If the string equals “*”, then the previous number (i.e. the number that accumulates
  the result) should be multiplied by the current one. If it is a “/”, then the previous number should be
  divided by the current one.
   */
  def term: Parser[Double] = factor ~ rep("*" ~ factor | "/" ~ factor) ^^ {
    case number ~ list => (number /: list) {
      case (acc, "*" ~ nextNum) => acc * nextNum
      case (acc, "/" ~ nextNum) => acc / nextNum
    }
  }

  //~> and <~ combinators
  /*
   | floatingPointNumber  | "(" ~ expr ~")" |

  Due to rewriting of the result of the floatingPointNumber parser to Double in the factor rule,
  we have to ensure that each alternative in factor also returns a Double. Otherwise we will not
  be able to perform computations.

  The result of the recursively defined expr should be a Double (after we have succeeded with the current issue).
  So the only remaining task is to strip parentheses from the sequence "(" ~ expr ~ ")".
  This can be done with the already known ^^ combinator:
     "(" ~ expr ~ ")" ^^ {case _ ~ e ~ _ => e}

  But there is also another, more concise way: if in the paar p ~ q only the left, or only the right part
  have to be retained for the further processing, then the combinators “<~”, or “~>” respectively can be
  used instead of “~”. The arrow points to the part that should be retained. So we can simply write:
    "(" ~> expr <~ ")"

   */
  def factor: Parser[Double] = floatingPointNumber ^^ { _.toDouble } | "(" ~> expr <~")"



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

  def main(args: Array[String]): Unit = {
    val input = "10.5 - 4*2"
    println("input : " + input)
    println(parseAll(expr, input))
  }
}
