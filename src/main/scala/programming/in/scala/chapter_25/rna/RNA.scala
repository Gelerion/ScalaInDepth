package programming.in.scala.chapter_25.rna

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer
import scala.collection.{IndexedSeqLike, mutable}

/**
  * Created by denis.shuvalov on 06/05/2018.
  */
final class RNA private (val groups: Array[Int], val length: Int)
  extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA] {

  import RNA._

  // Mandatory re-implementation of `newBuilder` in `IndexedSeq`
  override protected[this] def newBuilder: mutable.Builder[Base, RNA] =
    RNA.newBuilder

  // Mandatory implementation of `apply` in `IndexedSeq`
  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx) throw new IndexOutOfBoundsException
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }

  // Optional re-implementation of foreach, to make it more efficient
  override def foreach[U](f: Base => U): Unit = {
    var i = 0
    var b = 0
    while (i < length) {
      b = if (i % N == 0) groups(i / N) else b >>> S
      f(Base.fromInt(b & M))
      i += 1
    }
  }

  //The standard implementation of foreach in IndexedSeq will simply select every i'th element of the collection using
  //apply, where i ranges from 0 to the collection's length minus one. So this standard implementation selects an array
  // element and unpacks a base from it once for every element in an RNA strand. The overriding foreach in class RNA
  // is smarter than that. For every selected array element it immediately applies the given function to all bases
  // contained in it. So the effort for array selection and bit unpacking is much reduced.

}

object RNA {
  private val S = 2              //Number of bits necessary to represent group
  private val N = 32 / S         //Number of groups that fit in an Int
  private val M = (1 << S) - 1   //Bitmask to isolate a group

  def fromSeq(buf: Seq[Base]): RNA = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- buf.indices)
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    new RNA(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)

  def newBuilder: mutable.Builder[Base, RNA] = new ArrayBuffer mapResult fromSeq

  implicit def canBuildFrom: CanBuildFrom[RNA, Base, RNA] =
    new CanBuildFrom[RNA, Base, RNA] {
      //The apply() method simply creates a new builder of the right type
      override def apply(): mutable.Builder[Base, RNA] = newBuilder

      //By contrast, the apply(from) method takes the original collection as argument. This can be useful to adapt the
      //dynamic type of builder's return type to be the same as the dynamic type of the receiver. In the case of RNA this
      //does not come into play because RNA is a final class, so any receiver of static type RNA also has RNA as its
      //dynamic type. That's why apply(from) also simply calls newBuilder, ignoring its argument
      override def apply(from: RNA): mutable.Builder[Base, RNA] = newBuilder
    }
}
