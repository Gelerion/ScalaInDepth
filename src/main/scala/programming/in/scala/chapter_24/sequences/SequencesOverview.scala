package programming.in.scala.chapter_24.sequences

/**
  * Created by denis.shuvalov on 29/04/2018.
  *
  * The Seq trait represents sequences. A sequence is a kind of iterable that has a length and whose elements
  * have fixed index positions, starting from 0.
  */
object SequencesOverview {
  /*
  Each Seq trait has two subtraits, LinearSeq and IndexedSeq. These do not add any new operations, but each offers
  different performance characteristics. A linear sequence has efficient head and tail operations, whereas an indexed
  sequence has efficient apply, length, and (if mutable) update operations. List is a frequently used linear sequence,
  as is Stream. Two frequently used indexed sequences are Array and ArrayBuffer. The Vector class provides an interesting
  compromise between indexed and linear access. It has both effectively constant time indexing overhead and constant
  time linear access overhead. Because of this, vectors are a good foundation for mixed access patterns where both
  indexed and linear accesses are used.
   */

  /*
  Buffers
  An important sub-category of mutable sequences is buffers. Buffers allow not only updates of existing elements
  but also element insertions, element removals, and efficient additions of new elements at the end of the buffer.
  The principal new methods supported by a buffer are += and ++=, for element addition at the end, +=: and ++=: for
  addition at the front, insert and insertAll for element insertions, as well as remove and -= for element removal.

  Two Buffer implementations that are commonly used are ListBuffer and ArrayBuffer. As the name implies, a ListBuffer
  is backed by a List and supports efficient conversion of its elements to a List, whereas an ArrayBuffer is backed
  by an array, and can be quickly converted into one.
   */
}
