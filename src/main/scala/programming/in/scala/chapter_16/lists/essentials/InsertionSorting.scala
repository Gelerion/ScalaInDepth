package programming.in.scala.chapter_16.lists.essentials

/**
  * Created by denis.shuvalov on 26/03/2018.
  */
class InsertionSorting {

  def sort(xs: List[Int]): List[Int] =
    if (xs.isEmpty) Nil
    else insert(xs.head, sort(xs.tail))

  def insert(x: Int, xs: List[Int]): List[Int] =
    if (xs.isEmpty || x <= xs.head) x :: xs
    else xs.head :: insert(x, xs.tail)
}

object InsertionSortingExample extends App {
  val num = List(5,3,1,6,8)

  private val sorting = new InsertionSorting

  println(sorting.sort(num))
}