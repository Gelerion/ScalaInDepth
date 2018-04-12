package programming.in.scala.chapter_15.pattern.matching

import programming.in.scala.chapter_15.arithmetic.expressions.{BinOp, Expr, Number}

/**
  * Created by denis.shuvalov on 25/03/2018.
  */
class PatternGuards {
  /*
  Sometimes, syntactic pattern matching is not precise enough. For instance, say you are given the task of
  formulating a simplification rule that replaces sum expressions with two identical operands, such
  as e + e, by multiplications of two (e.g., e * 2). In the language of Expr trees, an expression like:

      BinOp("+", Var("x"), Var("x"))

  would be transformed by this rule to:

      BinOp("*", Var("x"), Number(2))
   */

  //You might try to define this rule as follows:
/*  scala> def simplifyAdd(e: Expr) = e match {
    case BinOp("+", x, x) => BinOp("*", x, Number(2))
    case _ => e
  }
  <console>:14: error: x is already defined as value x
    case BinOp("+", x, x) => BinOp("*", x, Number(2))
    ^*/

  //This fails because Scala restricts patterns to be linear: a pattern variable may only appear once in a pattern.

  //Pattern guard
  def simplifyAdd(e: Expr) = e match {
    case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2))
    case _ => e
  }

  //The guard can be an arbitrary boolean expression

  // match only positive integers
  //case n: Int if 0 < n => ...

  // match only strings starting with the letter `a'
  //case s: String if s(0) == 'a' => ...
}
