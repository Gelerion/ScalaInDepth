package programming.in.scala.chapter_16.lists.essentials

/**
  * Created by denis.shuvalov on 28/03/2018.
  */
class HighOrderMethods {
  // ----------- Mapping over lists: map, flatMap and foreach
  val ints = List(1, 2, 3)
  val words = List("the", "quick", "brown", "fox")

  ints map (_ + 1) //2,3,4
  words map (_.length)
  words map { _.toList.reverse.mkString } //List(eht, kciuq, nworb, xof)

  // range all pairs (i, j) such that 1 ≤ j < i < 5
  List.range(1, 5) flatMap (
    i => List.range(1, i) map (j => (i, j)) //List((2,1), (3,1), (3,2), (4,1), (4,2), (4,3))
  )

  // ----------- Filtering lists: filter, partition, find, takeWhile, dropWhile, and span
  ints filter(_ % 2 == 0)

  //The partition method is like filter but returns a pair of lists. One list contains all elements for which the
  //predicate is true, while the other contains all elements for which the predicate is false.
  //  xs partition p     equals    (xs filter p, xs filter (!p(_))
  List(1, 2, 3, 4, 5) partition (_ % 2 == 0) //(List(2, 4),List(1, 3, 5))

  //The find method is also similar to filter, but it returns the first element satisfying a given predicate
  List(1, 2, 3, 4, 5) find (_ % 2 == 0) //Option[Int] = Some(2)

  //The takeWhile and dropWhile operators also take a predicate as their right operand. The operation xs takeWhile
  //p takes the longest prefix of list xs such that every element in the prefix satisfies p. Analogously, the operation
  //xs dropWhile p removes the longest prefix from list xs such that every element in the prefix satisfies p.
  List(1, 2, 3, -4, 5) takeWhile (_ > 0) //List(1,2,3)

  words dropWhile(_ startsWith "t") //List(quick, brown, fox)

  //The span method combines takeWhile and dropWhile in one operation, just like splitAt combines take and drop.
  // It returns a pair of two lists, defined by the equality:
  //   xs span p    equals    (xs takeWhile p, xs dropWhile p)

  List(1, 2, 3, -4, 5) span (_ > 0) //(List(1, 2, 3),List(-4, 5))

  // ----------- Predicates over lists: forall and exists
  //The operation xs forall p takes as arguments a list xs and a predicate p. Its result is true if all elements in the list satisfy p.
  //Conversely, the operation xs exists p returns true if there is an element in xs that satisfies the predicate p.
  def hasZeroRow(m: List[List[Int]]) = m exists (row => row forall (_ == 0))

  // ----------- Folding lists: /: and :\
  //Another common kind of operation combines the elements of a list with some operator. For instance:
  //   sum(List(a, b, c))    equals    0 + a + b + c
  //his is a special instance of a fold operation:
  def sum(xs: List[Int]): Int = (0 /: xs) { _ + _ }
  def product(xs: List[Int]): Int = (1 /: xs) (_ * _)
  //A fold left operation "(z /: xs) (op)" involves three objects: a start value z, a list xs, and a binary operation op.
  //(z /: List(a, b, c)) (op)    equals    op(op(op(z, a), b), c)

  ("" /: words) (_ + " " + _) //" the quick brown fox"
  (words.head /: words.tail) (_ + " " + _) //the quick brown fox

  //The /: operator produces left-leaning operation trees (its syntax with the slash rising forward is intended to be
  //a reflection of that). The operator has :\ as an analog that produces right-leaning trees.
  //(List(a, b, c) :\ z) (op)    equals    op(a, op(b, op(c, z)))

  def flatternLeft[T](xss: List[List[T]]) =
    (List[T]() /: xss) (_ ::: _)

  def flatternRight[T](xss: List[List[T]]) =
    (xss :\ List[T]()) (_ ::: _)

  //Because list concatenation, xs ::: ys, takes time proportional to its first argument xs, the implementation in
  //terms of fold right in flattenRight is more efficient than the fold left implementation in flattenLeft.
  //The problem is that flattenLeft(xss) copies the first element list xss.head n-1 times, where n is the length of the list xss.

  // ----------- Sorting lists: sortWith
  //The operation xs sortWith before, where "xs" is a list and "before" is a function that can be used to compare two
  //elements, sorts the elements of list xs. The expression x before y should return true if x should come
  //before y in the intended ordering for the sort.
  List(1,-3,4,2,6) sortWith (_ < _)
  //Note that sortWith performs a merge sort similar to the msort algorithm shown in the last section.
  //But sortWith is a method of class List, whereas msort is defined outside lists
}
