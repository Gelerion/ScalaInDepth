package programming.in.scala.chapter_15.arithmetic.expressions

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
object ArithmeticPatternMatching extends App {

  //Say you want to simplify arithmetic expressions of the kinds just presented. There is a multitude
  //of possible simplification rules. The following three rules just serve as an illustration:

/*  UnOp("-", UnOp("-", null))  => null   // Double negation
  BinOp("+", null, Number(0)) => null   // Adding zero
  BinOp("*", null, Number(1)) => null   // Multiplying by one*/

  def simplifyTop(expr: Expr): Expr = expr match {
    // expressions never "fall through" into the next case
    case UnOp("-", UnOp("-", e))  => e // Double negation
    case BinOp("+", e, Number(0)) => e // Adding zero
    case BinOp("*", e, Number(1)) => e // Multiplying by one
    // if none of the patterns match, an exception named MatchError is thrown
    case _ => expr
  }

  //The right-hand side of simplifyTop consists of a match expression. match corresponds to switch in Java,
  //but it's written after the selector expression. In other words, it's:
  //  selector match { alternatives }

  //A pattern match includes a sequence of alternatives, each starting with the keyword case. Each alternative includes
  //a pattern and one or more expressions, which will be evaluated if the pattern matches. An arrow symbol => separates
  //the pattern from the expressions.

  //A constant pattern:
  //   like "+" or 1 matches values that are equal to the constant with respect to ==
  //A variable pattern:
  //   like e matches every value
  //A constructor pattern:
  //   like UnOp("-", e). This pattern matches all values of type UnOp whose first argument matches "-" and whose second argument matches e.

  //Wildcard patterns
  //The wildcard pattern (_) matches any object whatsoever.
  //Wildcards can also be used to ignore parts of an object that you do not care about.

/*  expr match {
    case BinOp(_, _, _) => println(expr + " is a binary operation")
    case _ => println("It's something else")
  } //result in Unit
  */

  def simplifyAll(expr: Expr): Expr = expr match {
    case UnOp("-", UnOp("-", e)) =>
      simplifyAll(e) // `-' is its own inverse
    case BinOp("+", e, Number(0)) =>
      simplifyAll(e) // `0' is a neutral element for `+'
    case BinOp("*", e, Number(1)) =>
      simplifyAll(e) // `1' is a neutral element for `*'
    case UnOp(op, e) =>
      UnOp(op, simplifyAll(e)) //matches every unary operation
    case BinOp(op, l, r) =>
      BinOp(op, simplifyAll(l), simplifyAll(r))
    case _ => expr
  }
}
