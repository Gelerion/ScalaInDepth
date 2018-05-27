package programming.in.scala.chapter_35.scells

import programming.in.scala.chapter_35.Model

import scala.collection.mutable

/**
  * Created by denis.shuvalov on 27/05/2018.
  */
trait Evaluator {
  this: Model =>

  def evaluate(e: Formula) : Double = try {
    e match {
      case Coord(row, column) =>
        cells(row)(column).value
      case Number(v) =>
        v
      case Textual(_) =>
        0
      case Application(function, arguments) =>
        val argvals = arguments flatMap evalList
        operations(function)(argvals)
    }
  } catch {
    case ex: Exception => Double.NaN
  }

  // operations table maps function names to function objects
  type Op = List[Double] => Double
  val operations = new mutable.HashMap[String, Op]

  //The evaluation of arguments is different from the evaluation of top-level formulas. Arguments may be lists whereas
  //top-level functions may not. For instance, the argument expression A1:A3 in sum(A1:A3) returns the values of cells
  //A1, A2, A3 in a list. This list is then passed to the sum operation. It's also possible to mix lists and single
  //values in argument expressions, for instance the operation sum(A1:A3, 1.0, C7), which would sum up five values.
  //To handle arguments that might evaluate to lists, there's another evaluation function, called evalList, which
  //takes a formula and returns a list of values:
  private def evalList(e: Formula): List[Double] = e match {
    case Range(_,_) => references(e) map (_.value)
    case _ => List(evaluate(e))
  }

  def references(e: Formula): List[Cell] = e match {
    case Coord(row, column) =>
      List(cells(row)(column))
    case Range(Coord(r1, c1), Coord(r2, c2)) =>
      for (row <- (r1 to r2).toList; column <- c1 to c2)
        yield cells(row)(column)
    case Application(function, arguments) =>
      arguments flatMap references
    case _ =>
      List()
  }
}
