package programming.in.scala.chapter_25.rna

/**
  * Created by denis.shuvalov on 03/05/2018.
  *
  * Sequence type for RNA strands, which are sequences of bases A (adenine), T (thymine), G (guanine), and U (uracil).
  *
  * The next task is to define a class for strands of RNA. Conceptually, a strand of RNA is simply a Seq[Base]. However,
  * RNA strands can get quite long, so it makes sense to invest some work in a compact representation. Because there are
  * only four bases, a base can be identified with two bits, and you can therefore store sixteen bases as two-bit values
  * in an integer. The idea, then, is to construct a specialized subclass of Seq[Base], which uses this packed
  * representation.
  */
class RNA1 private(val groups: Array[Int], val length: Int) extends IndexedSeq[Base] {

  import RNA1._

  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx)
      throw new IndexOutOfBoundsException
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)

    //groups(idx / N) - to which group it belongs, each group can hold up to 16 values
    //(idx % N * S) - get bit offset we are interested in
    //groups(idx / N) >> (idx % N * S) - shift group's int bits to right
    //& M - drop all non relevant bits
  }
}

object RNA1 {

  //Number of bits necessary to represent group
  private val S = 2

  //Number of groups that fit in an Int
  private val N = 32 / S

  //Bitmask to isolate a group
  private val M = (1 << S) - 1

  def fromSeq(buf: Seq[Base]): RNA1 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- buf.indices)
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)
    new RNA1(groups, buf.length)
  }

  def apply(bases: Base*) = fromSeq(bases)

}
