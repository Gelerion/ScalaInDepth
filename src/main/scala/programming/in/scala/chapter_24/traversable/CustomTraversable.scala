package programming.in.scala.chapter_24.traversable

/**
  * Created by denis.shuvalov on 23/04/2018.
  */
class CustomTraversable[+Elem] extends Traversable[Elem] {
  //At the top of the collection hierarchy is trait Traversable.

  //Its only abstract operation is foreach:
  override def foreach[U](f: Elem => U): Unit = ???
  /*
  The foreach method is meant to traverse all elements of the collection, and apply the given operation, f, to each element.
  The type of the operation is Elem => U, where Elem is the type of the collection's elements and U is an arbitrary result
  type. The invocation of f is done for its side effect only; in fact any function result of f is discarded by foreach.
   */

  /*
  Additional methods:
  ---------------------------------------------------------------------------------------
  1. Addition ++, which appends two traversables together, or appends all elements of an iterator to a traversable.
  2. Map operations map, flatMap, and collect, which produce a new collection by applying some function to collection elements.
  3. Conversions toIndexedSeq, toIterable, toStream, toArray, toList, toSeq, toSet, and toMap, which turn a Traversable
     collection into a more specific collection. All these conversions return the receiver object if it already matches
     the demanded collection type. For instance, applying toList to a list will yield the list itself.
  4. Copying operations copyToBuffer and copyToArray. As their names imply, these copy collection elements to a
     buffer or array, respectively.
  5. Size operations isEmpty, nonEmpty, size, and hasDefiniteSize. Collections that are traversable can be finite or
     infinite. An example of an infinite traversable collection is the stream of natural numbers Stream.from(0).
     The method hasDefiniteSize indicates whether a collection is possibly infinite. If hasDefiniteSize returns true,
     the collection is certainly finite. If it returns false, the collection might be infinite, in which case size
     will emit an error or not return.
   */

  /*
  You might wonder why the extra trait Traversable is above Iterable. Can we not do everything with an iterator?
  So what's the point of having a more abstract trait that defines its methods in terms of foreach instead of iterator?
  One reason for having Traversable is that sometimes it is easier or more efficient to provide an implementation of
  foreach than to provide an implementation of iterator.
   */
}

//traversable presence explanation
sealed abstract class Tree
case class Branch(left: Tree, right: Tree)
case class Node(elem: Int) extends Tree

//Now assume you want to make trees traversable. To do this, have
//Tree inherit from Traversable[Int] and define a foreach method.
//That's not too hard, and it is also very efficient—traversing a balanced tree takes time proportional to
//the number of elements in the tree. To see this, consider that for a balanced tree with N leaves you will have
//N - 1 interior nodes of class Branch. So the total number of steps to traverse the tree is N + N - 1.
sealed abstract class TreeTraversable extends Traversable[Int] {
  override def foreach[U](f: Int => U): Unit = this match {
    case NodeT(elem) => f(elem)
    case BranchT(left, right) => left foreach f; right foreach f
  }
}
case class BranchT(left: TreeTraversable, right: TreeTraversable) extends TreeTraversable
case class NodeT(elem: Int) extends TreeTraversable

//Now, compare this with making trees iterable. To do this, have Tree inherit from Iterable[Int]
//and define an iterator method.
sealed abstract class TreeIterable extends Iterable[Int] {
  override def iterator: Iterator[Int] = this match {
    case NodeI(elem) => Iterator.single(elem)
    case BranchI(left, right) => left.iterator ++ right.iterator
  }
}
case class BranchI(left: TreeIterable, right: TreeIterable) extends TreeIterable
case class NodeI(elem: Int) extends TreeIterable
//However, there's an efficiency problem that has to do with the implementation of the iterator concatenation method, ++.
//Every time an element is produced by a concatenated iterator such as l.iterator ++ r.iterator, the computation needs
//to follow one indirection to get at the right iterator (either l.iterator, or r.iterator). Overall, that makes \log(N)
//indirections to get at a leaf of a balanced tree with N leaves. So the cost of visiting all elements of a tree went up
//from about 2N for the foreach traversal method to N \log(N) for the traversal with iterator. If the tree has a million
//elements that means about two million steps for foreach and about twenty million steps for iterator.
//So the foreach solution has a clear advantage