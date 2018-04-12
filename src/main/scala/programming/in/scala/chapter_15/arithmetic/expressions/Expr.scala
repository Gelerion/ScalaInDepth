package programming.in.scala.chapter_15.arithmetic.expressions

/**
  * Created by denis.shuvalov on 19/03/2018.
  */
//Whenever you write a pattern match, you need to make sure you have covered all of the possible cases.
//The alternative is to make the superclass of your case classes sealed.
//A sealed class cannot have any new subclasses added except the ones in the same file.
//This is very useful for pattern matching because it means you only need to worry about the subclasses you already know about
sealed abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
