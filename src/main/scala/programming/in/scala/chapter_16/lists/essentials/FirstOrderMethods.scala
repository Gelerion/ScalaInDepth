package programming.in.scala.chapter_16.lists.essentials

/**
  * Created by denis.shuvalov on 27/03/2018.
  *
  * A method is first-order if it does not take any functions as arguments.
  */
class FirstOrderMethods {

  //concatenating lists
  //Concatenation (:::) is implemented as a method in class List
  val ints = List(1, 2) ::: List(3, 4, 5)
  val fruit = List("apples", "oranges", "pears")

  //Like cons, list concatenation associates to the right. An expression like this:
  // xs ::: ys ::: zs  -> xs ::: (ys ::: zs)

  //On lists, unlike arrays, length is a relatively expensive operation. It needs to traverse the whole list to
  //find its end, and therefore takes time proportional to the number of elements in the list.
  val length: Int = List(1, 2, 3).length

  // ---------------- Accessing the end of a list: init and last
  //Unlike head and tail, which both run in constant time, init and last need to traverse the whole list to compute their result
  val abcde = List('a', 'b', 'c', 'd', 'e')
  //Like head and tail, these methods throw an exception when applied to an empty list
  abcde.init //abcd
  abcde.last //e

  //Reversing lists: reverse
  //Like all other list operations, reverse creates a new list rather than changing the one it operates on.
  //Since lists are immutable, such a change would not be possible anyway
  abcde.reverse //e, d, c, b, a

  //1. reverse is its own inverse
  //      xs.reverse.reverse  equals  xs
  //2. reverse turns init to tail and last to head, except that the elements are reversed:
  //      xs.reverse.init  equals  xs.tail.reverse
  //      xs.reverse.tail  equals  xs.init.reverse
  //      xs.reverse.head  equals  xs.last
  //      xs.reverse.last  equals  xs.head
  // see custom implementation rev in CustomListFunc


  // ---------------- Prefixes and suffixes: drop, take, and splitAt
  //The drop and take operations generalize tail and init in that they return arbitrary prefixes or suffixes of a list.
  //The expression "xs take n" returns the first n elements of the list xs. If n is greater than xs.length, the whole
  //list xs is returned. The operation "xs drop n" returns all elements of the list xs, except for the first n ones.
  //If n is greater than xs.length, the empty list is returned.

  //The splitAt operation splits the list at a given index, returning a pair of two lists. It is defined by the equality:
  //    xs splitAt n    equals    (xs take n, xs drop n)

  abcde take 2 // List(a,b)
  abcde drop 2 // List(c,d,e)
  abcde splitAt 2 // (List(a, b), List(c, d, e))

  // ---------------- Element selection: apply and indices
  abcde apply 2 //c
  abcde(2) //c
  //One reason why random element selection is less popular for lists than for arrays is that xs(n) takes time
  //proportional to the index n. In fact, apply is simply defined by a combination of drop and head:
  //  xs apply n    equals    (xs drop n).head
  abcde.indices //Range(0, 1, 2, 3, 4)

  // flattering a list of lists
  List(List(1, 2), List(3), List(), List(4, 5)).flatten //List(1, 2, 3, 4, 5)
  fruit.map(_.toCharArray).flatten //List(a, p, p, l, e, s, o, r, a, n, g, e, s, p, e, a, r, s)
  //! It can only be applied to lists whose elements are all lists. Trying to flatten any other list will give a compilation error

  // ---------------- Zipping lists: zip and unzip
  abcde.indices zip abcde //Vector((0,a), (1,b), (2,c), (3,d), (4,e))
  //If the two lists are of different length, any unmatched elements are dropped
  abcde.zipWithIndex //List((a,0), (b,1), (c,2), (d,3), (e,4))

  //Any list of tuples can also be changed back to a tuple of lists by using the unzip method:
//  zipped.unzip (List[Char], List[Int]) = (List(a, b, c),List(1, 2, 3))

  // -- mkString
  //xs mkString (pre, sep, post)
  abcde.mkString("[", ",", "]")

  //There are also variants of the mkString methods called addString which append the constructed string to a StringBuilder object
  val buf = new StringBuilder
  abcde addString (buf, "(", ";", ")")
  //The mkString and addString methods are inherited from List's super trait Traversable, so they are applicable to all other collections as well.

  // ---------------- Converting lists: iterator, toArray, copyToArray
  abcde toArray
  //There's also a method copyToArray, which copies list elements to successive array positions within some
  //destination array. The operation:
  //    xs copyToArray (arr, start)
  val arr2 = new Array[Int](10)
  List(1,2,3) copyToArray (arr2, 3) //Array(0, 0, 0, 1, 2, 3, 0, 0, 0, 0)


}
