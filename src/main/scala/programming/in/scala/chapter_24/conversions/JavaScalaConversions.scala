package programming.in.scala.chapter_24.conversions

/**
  * Created by denis.shuvalov on 30/04/2018.
  */
object JavaScalaConversions {
  /*
  pass one of Scala's collections to a Java method that expects the Java counterpart. It is quite easy to do this,
  because Scala offers implicit conversions between all the major collection types in the JavaConversions object.
  In particular, you will find bidirectional conversions between the following types:

  Iterator         \null    java.util.Iterator
  Iterator         \null    java.util.Enumeration
  Iterable         \null    java.lang.Iterable
  Iterable         \null    java.util.Collection
  mutable.Buffer   \null    java.util.List
  mutable.Set      \null    java.util.Set
  mutable.Map      \null    java.util.Map

  To enable these conversions, simply import like this:

  scala> import collection.JavaConversions._
  import collection.JavaConversions._
   */
}
