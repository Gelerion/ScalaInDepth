package programming.in.scala.chapter_25.rna

import scala.language.postfixOps

/**
  * Created by denis.shuvalov on 06/05/2018.
  */
object RNA2Test extends App {

  val rna2 = RNA2(A, U, G, G, T)

  println(rna2 take 3) //RNA2(A, U, G)
  println(rna2 filter (U !=)) //RNA2(A, G, G, T)

  //------------
  /*
  There is another class of methods in collections that we haven't dealt with yet. These methods do not always return
  the collection type exactly. They might return the same kind of collection, but with a different element type.
  The classical example of this is the map method. If s is a Seq[Int], and f is a function from Int to String, then s.map(f)
  would return a Seq[String]. So the element type changes between the receiver and the result, but the kind of collection
  stays the same.

  There are a number of other methods that behave like map. For some of them you would expect this (e.g., flatMap, collect),
  but for others you might not. For instance, the append method, ++, also might return a result whose type differs from that
  of its arguments—appending a list of String to a list of Int would give a list of Any. How should these methods be adapted
  to RNA strands? Ideally we'd expect that mapping bases to bases over an RNA strand would yield again an RNA strand:
   */

  val rnaMapped = rna2 map {
    case A => T
    case b => b
  }

  println(rnaMapped) // !! Vector(T, U, G, G, T)

  //On the other hand, mapping bases to some other type over an RNA strand cannot yield another RNA strand because the
  //new elements have the wrong type. It has to yield a sequence instead. In the same vein appending elements that are
  //not of type Base to an RNA strand can yield a general sequence, but it cannot yield another RNA strand.

  rna2 map Base.toInt //IndexedSeq[Int] = Vector(0, 3, 2, 2, 1)
  val mixed = rna2 ++ List("missing", "data") //IndexedSeq[java.lang.Object] = Vector(A, U, G, G, T, missing, data)
}
