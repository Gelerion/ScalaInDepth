package programming.in.scala.chapter_16.lists.essentials

/**
  * Created by denis.shuvalov on 01/04/2018.
  */
class ListCompanion {
  // -- Creating a range of numbers: List.range
  List.range(1, 5) //1,2,3,4
  //with step
  List.range(1, 9, 2) //1, 3, 5, 7
  List.range(9, 1, -3) //9, 6, 3

  // -- Creating uniform lists: List.fill
  List.fill(5)('a') //List(a, a, a, a, a)
  //If fill is given more than two arguments, then it will make multi-dimensional lists
  List.fill(2, 3)('b') //List(List(b, b, b), List(b, b, b))

  // -- Tabulating a function: List.tabulate
  //The tabulate method creates a list whose elements are computed according to a supplied function. Its arguments are
  //just like those of List.fill: the first argument list gives the dimensions of the list to create, and the second
  //describes the elements of the list. The only difference is that instead of the elements being fixed, they are computed from a function
  List.tabulate(5)(n => n * n) //List(0, 1, 4, 9, 16)
  // List.tabulate(5,5)(_ * _)

  // -- Concatenating multiple lists: List.concat
  List.concat(List('a', 'b'), List('c')) //List(a, b, c)
}
