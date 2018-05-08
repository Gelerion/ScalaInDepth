package programming.in.scala.chapter_25.rna

/**
  * Created by denis.shuvalov on 03/05/2018.
  */
object RNA1Test extends App {
  val xs = List(A, G, T, A, U) //List[Product with Base]
  println(xs)

  val rna: RNA1 = RNA1.fromSeq(xs)
  println(rna) //RNA1(A, G, T, A, U)

  println(rna(4)) //U

  println(rna.length) //5
  println(rna.last) //U

  private val seq: IndexedSeq[Base] = rna.take(3)
  println(seq) // !! Vector(A, G, T)
  //In fact, you see an IndexedSeq[Base] as static result type and a Vector as the dynamic type of the result value.
  //You might have expected to see an RNA1 value instead.
  //Class IndexedSeq, on the other hand, has a take method that returns an IndexedSeq, and that's implemented in
  //terms of IndexedSeq's default implementation, Vector.

  /*
  Now that you understand why things are the way they are, the next question should be what needs to be done to change
  them? One way to do this would be to override the take method in class RNA1, maybe like this:
      def take(count: Int): RNA1 = RNA1.fromSeq(super.take(count))
  This would do the job for take. But what about drop, or filter, or init? In fact there are over fifty methods on
  sequences that return again a sequence. For consistency, all of these would have to be overridden. This looks less and
  less like an attractive option.

  Fortunately, there is a much easier way to achieve the same effect. The RNA class needs to inherit not only from
  IndexedSeq, but also from its implementation trait IndexedSeqLike.
   */

}
