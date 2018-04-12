package programming.in.scala.chapter_16.lists.essentials.concatenation

/**
  * Created by denis.shuvalov on 27/03/2018.
  */
object CustomListFunc extends App {

  //To design the implementation of append, it pays to remember the "divide and conquer" design principle
  //for programs over recursive data structures such as lists
  //Many algorithms over lists first split an input list into simpler cases using a pattern match
  //That's the divide part of the principle.
  //They then construct a result for each case. If the result is a non-empty list, some of its parts may be
  //constructed by recursive invocations of the same algorithm. That's the conquer part of the principle.
  def append[T](xs: List[T], ys: List[T]): List[T] = { //:::
    //To apply this principle to the implementation of the append method, the first question to ask is on which list to match.

    //Since lists are constructed from the back towards the front, ys can remain intact, whereas xs will need to be
    //taken apart and prepended to ys. Thus, it makes sense to concentrate on xs as a source for a pattern match.
    //The most common pattern match over lists simply distinguishes an empty from a non-empty list.

    xs match {
      // In this case concatenation yields the second list:
      case List() => ys
      // xs consists of some head x followed by a tail xs1
      // first element of the result list is x
      case x :: xs1 => x :: append(xs1, ys)
    }
  }


  def rev[T](xs: List[T]): List[T] = {
    xs match {
      case List() => xs
      case x :: xs1 => rev(xs1) ::: List(x)
//      case x :: xs1 => append(rev(xs1), List(x))
    }
  }
  //Notice that there are n recursive calls to rev. Each call except the last involves a list concatenation.
  //List concatenation xs ::: ys takes time proportional to the length of its first argument xs.
  //Hence, the total complexity of rev is:
  //    n + (n - 1) + ... + 1 = (1 + n) * n / 2

  //linear time
  //def reverseLeft[T](xs: List[T]) = (startvalue /: xs)(operation)
  def reverseLeft[T](xs: List[T]) = (List[T]() /: xs) {(ys, y) => y :: ys}

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {

    def merge(xs: List[T], ys: List[T]): List[T] = {
      (xs, ys) match {
        case (_, Nil) => xs
        case (Nil, _) => ys
        case (x :: xs1, y :: ys1) =>
          if(less(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }
    }

    val mid = xs.length / 2
    if(mid == 0) xs
    else {
      val (ys, zs) = xs splitAt mid
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

  val abcde = List('a', 'b', 'c', 'd', 'e')
  println(rev(abcde))

  println("Merge sort:")

  //The msort function is a classical example of the currying concept
  val intSort = msort((x: Int, y: Int) => x < y) _
  //println(msort((x: Int, y: Int) => x < y)(List(5, 7, 3, 1)))
  println(intSort(List(5, 7, 3, 1)))

}
