package programming.in.scala.chapter_15.pattern.matching

import programming.in.scala.chapter_15.arithmetic.expressions.{Expr, Number, Var}

/**
  * Created by denis.shuvalov on 25/03/2018.
  */
object SealedClasses extends App {

  // warning: match is not exhaustive!
//  missing combination           UnOp
//  missing combination          BinOp
  def describe(e: Expr): String = e match {
    case Number(_) => "a number"
    case Var(_) => "a variable"
  }

  //However, at times you might encounter a situation where the compiler is too picky in emitting the warning.
  //For instance, you might know from the context that you will only ever apply the describe method above
  //to expressions that are either Numbers or Vars, so you know that no MatchError will be produced.
  //To make the warning go away, you could add a third catch-all case to the method, like this:
/*  def describe(e: Expr): String = e match {
    case Number(_) => "a number"
    case Var(_) => "a variable"
    case _ => throw new RuntimeException // Should not happen
  }*/

  //A more lightweight alternative is to add an @unchecked annotation to the selector expression of the match.
  def describeNoWarn(e: Expr): String = (e: @unchecked) match {
    case Number(_) => "a number"
    case Var(_) => "a variable"
  }
}
