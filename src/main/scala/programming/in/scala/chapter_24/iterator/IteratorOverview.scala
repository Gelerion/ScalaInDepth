package programming.in.scala.chapter_24.iterator

/**
  * Created by denis.shuvalov on 30/04/2018.
  */
object IteratorOverview {

  /*
  In summary, iterators behave like collections if you never access an iterator again after invoking a method on it.
  The Scala collection libraries make this explicit with an abstraction called TraversableOnce, which is a common
  super trait of Traversable and Iterator. As the name implies, TraversableOnce objects can be traversed using foreach,
  but the state of that object after the traversal is not specified. If the TraversableOnce object is in fact an Iterator,
  it will be at its end after the traversal, but if it is a Traversable, it will still exist as before. A common use case
  of TraversableOnce is as an argument type for methods that can take either an iterator or traversable as argument.
  An example is the appending method ++ in trait Traversable. It takes a TraversableOnce parameter, so you can append
  elements coming from either an iterator or a traversable collection.
   */

  //--------------------------------------------------------------------------------

  /*
  Sometimes you want an iterator that can "look ahead" so that you can inspect the next element to be returned without
  advancing past that element. Consider, for instance, the task to skip leading empty strings from an iterator that
  returns a sequence of strings. You might be tempted to write something like the following method:

  // This won't work
  def skipEmptyWordsNOT(it: Iterator[String]) = {
    while (it.next().isEmpty) {}
  }

  But looking at this code more closely, it's clear that this is wrong: the code will indeed skip leading empty strings,
  but it will also advance it past the first non-empty string!

  The solution to this problem is to use a buffered iterator, an instance of trait BufferedIterator. BufferedIterator is a
  subtrait of Iterator, which provides one extra method, head. Calling head on a buffered iterator will return its first
  element, but will not advance the iterator. Using a buffered iterator, skipping empty words can be written like this:

  def skipEmptyWords(it: BufferedIterator[String]) =
    while (it.head.isEmpty) { it.next() }'

  Every iterator can be converted to a buffered iterator by calling its buffered method.
   */
}
