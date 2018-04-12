package programming.in.scala.chapter_17.sets_maps

import scala.collection.immutable.TreeSet

/**
  * Created by denis.shuvalov on 03/04/2018.
  */
class SortedExample {
  /*
  On occasion you may need a set or map whose iterator returns elements in a particular order. For this purpose,
  the Scala collections library provides traits SortedSet and SortedMap. These traits are implemented by classes
  TreeSet and TreeMap, which use a red-black tree to keep elements (in the case of TreeSet) or keys (in the case of
  TreeMap) in order. The order is determined by the Ordered trait, which the element type of the set, or key type of
  the map, must either mix in or be implicitly convertible to. These classes only come in immutable variants
   */

  val ts = TreeSet(9, 3, 1, 8, 0, 2, 7, 4, 6, 5)
}
