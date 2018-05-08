package programming.in.scala.chapter_25.rna

import scala.collection.mutable.ArrayBuffer
import scala.collection.{IndexedSeqLike, mutable}

/**
  * Created by denis.shuvalov on 06/05/2018.
  */
final class RNA2 private (val groups: Array[Int], val length: Int)
  extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA2] {

  import RNA2._

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

  /*
  To construct this builder, it first creates an ArrayBuffer, which itself is a Builder[Base, ArrayBuffer]. It then
  transforms the ArrayBuffer builder by calling its mapResult method to an RNA2 builder. The mapResult method expects a
  transformation function from ArrayBuffer to RNA2 as its parameter.
   */
  override def newBuilder: mutable.Builder[Base, RNA2] =
    new ArrayBuffer[Base] mapResult fromSeq

  /*
  The map method is originally defined in class scala.collection.TraversableLike with the following signature:

  def map[B, That](f: Elem => B)
    (implicit cbf: CanBuildFrom[Repr, B, That]): That

  Here Elem is the type of elements of the collection, and Repr is the type of the collection itself; that is, the second
  type parameter that gets passed to implementation classes such as TraversableLike and IndexedSeqLike. The map method
  takes two more type parameters, B and That. The B parameter stands for the result type of the mapping function, which
  is also the element type of the new collection. The That appears as the result type of map, so it represents the type
  of the new collection that gets created.

  How is the That type determined? It is linked to the other types by an implicit parameter cbf, of type CanBuildFrom[Repr, B, That].
  These CanBuildFrom implicits are defined by the individual collection classes. In essence, an implicit value of type
  CanBuildFrom[From, Elem, To] says: "Here is a way, given a collection of type From, to build with elements of type
  Elem a collection of type To."
   */
}

object RNA2 {

  //Number of bits necessary to represent group
  private val S = 2

  //Number of groups that fit in an Int
  private val N = 32 / S

  //Bitmask to isolate a group
  private val M = (1 << S) - 1

  def fromSeq(buf: Seq[Base]): RNA2 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- buf.indices)
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    new RNA2(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)

}