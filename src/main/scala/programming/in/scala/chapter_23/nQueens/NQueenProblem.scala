package programming.in.scala.chapter_23.nQueens

/**
  * Created by denis.shuvalov on 17/04/2018.
  *
  * A particularly suitable application area of for expressions are combinatorial puzzles. An example of such a
  * puzzle is the 8-queens problem: Given a standard chess-board, place eight queens such that no queen is in check
  * from any other (a queen can check another piece if they are on the same column, row, or diagonal).
  * To find a solution to this problem, it's actually simpler to generalize it to chess-boards of arbitrary size.
  * Hence, the problem is to place N queens on a chess-board of N x N squares, where the size N is arbitrary.
  */
object NQueenProblem extends App {

  println(queens(4))

  /**
    * @param n size of the board
    * @return Each solution presented by a list of length k of coordinates (row, column),
    *         where both row and column numbers range from 1 to N.
    *         It's convenient to treat these partial solution lists as stacks, where the coordinates of
    *         the queen in row k come first in the list, followed by the coordinates of the queen
    *         in row k-1, and so on. The bottom of the stack is the coordinate of the queen placed
    *         in the first row of the board.
    *         All solutions together are represented as a list of lists, with one element for each solution
    */
  def queens(n: Int): List[List[(Int, Int)]] = {
    //generate all partial solutions of length k in a list
    def placeQueens(k: Int): List[List[(Int, Int)]] = {
      if(k == 0) List(List())
      else
        for {
          queens <- placeQueens(k - 1)
          column <- 1 to n // inner loop
          queen = (k, column)
          if isSafe(queen, queens)
        } yield queen :: queens
    }

    placeQueens(n)
  }

  def isSafe(queen: (Int, Int), queens: List[(Int, Int)]): Boolean = {
    print(s"Check $queen into $queens")
    val res = queens forall(!inCheck(_, queen))
    println(s" ${ if(res) "safe" else "in check"}")
    res
  }

  def inCheck(q1: (Int, Int), q2: (Int, Int)) =
    q1._1 == q2._1 || //same row
    q1._2 == q2._2 || //same column
    (q1._1 - q2._1).abs == (q1._2 - q2._2).abs //on diagonal
}
