package programming.in.scala.chapter_24.iterable

/**
  * Created by denis.shuvalov on 24/04/2018.
  */
object IterableOverview extends App {

  /*
  All methods in this trait are defined in terms of an abstract method, iterator, which yields the collection's
  elements one by one. The abstract foreach method inherited from trait Traversable is implemented in Iterable in
  terms of iterator. Here is the actual implementation:

  def foreach[U](f: Elem => U): Unit = {
    val it = iterator
    while (it.hasNext) f(it.next())
  }
   */

  /*
  Two more methods exist in Iterable that return iterators: grouped and sliding. These iterators, however, do not
  return single elements but whole subsequences of elements of the original collection. The maximal size of these
  subsequences is given as an argument to these methods. The grouped method chunks its elements into increments,
  whereas sliding yields a sliding window over the elements.
   */
  val xs = List(1, 2, 3, 4, 5)
  val grouped: Iterator[List[Int]] = xs grouped 3
  grouped.foreach(println) //List(1, 2, 3), List(4, 5)

  val sliding: Iterator[List[Int]] = xs sliding 3
  sliding.foreach(println) //List(1, 2, 3), List(2, 3, 4), List(3, 4, 5)

  /*
  Subcategories of Iterable
  In the inheritance hierarchy below Iterable you find three traits: Seq, Set, and Map. A common aspect of these three
  traits is that they all implement the PartialFunction trait[1] with its apply and isDefinedAt methods. However, the
  way each trait implements PartialFunction differs.

  For sequences, apply is positional indexing, where elements are always numbered from 0. That is, Seq(1, 2, 3)(1) == 2.
  For sets, apply is a membership test. For instance, Set('a', 'b', 'c')('b') == true whereas Set()('a') == false.
  Finally for maps, apply is a selection. For instance, Map('a' -> 1, 'b' -> 10, 'c' -> 100)('b') == 10.

  In the following three sections, we will explain each of the three kinds of collections in more detail.
   */


}
