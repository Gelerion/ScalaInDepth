package programming.in.scala.chapter_33.arithmetic.expressions

import scala.util.parsing.combinator.JavaTokenParsers

object ArithParserAndEvaluatorV3 extends JavaTokenParsers{

  //Definitions of arithmetic functions
  //Inline definitions The first attempt results in the following definition:

  // def expr: Parser[Double] = chainl1(term, "+" ^^^ {_+_} | "-" ^^^ {_-_})
  // Unfortunately, this definition is not working.

  // Therefore we should specify the parameter types in our functions explicitly:
  // "+" ^^^ {(a: Double , b: Double) => a + b}

  def expr: Parser[Double]   = term * ("+" ^^^ Add | "-" ^^^ Sub)
  def term: Parser[Double]   = factor * ("*" ^^^ Mul | "/" ^^^ Div)
  def factor: Parser[Double] = floatingPointNumber ^^ Number | "(" ~> expr <~ ")"

  val Mul = (a: Double, b: Double) => a * b
  //or val Mul = (_:Double) * (_:Double)
  val Div = (a: Double, b: Double) => a / b
  val Add = (a: Double, b: Double) => a + b
  val Sub = (a: Double, b: Double) => a - b
  val Number = (a: String) => a.toDouble

  def main(args: Array[String]): Unit = {
    val input = "10.5 - 4*2"
    println("input : " + input)
    println(parseAll(expr, input))
  }
}
