package programming.in.scala.chapter_24.streams

/**
  * Created by denis.shuvalov on 30/04/2018.
  *
  * A stream is like a list except that its elements are computed lazily. Because of this, a stream can be
  * infinitely long. Only those elements requested will be computed. Otherwise, streams have the same performance characteristics as lists.
  */
object StreamExample extends App {

  val str = 1 #:: 2 #:: 3 #:: Stream.empty
  //immutable.Stream[Int] = Stream(1, ?)

  //The head of this stream is 1, and the tail of it has 2 and 3. The tail is not printed here, though, because it
  //hasn't been computed yet! Streams are required to compute lazily, and the toString method of a stream is careful
  //not to force any extra evaluation.

  def fibFrom(a: Int, b: Int): Stream[Int] = {
    a #:: fibFrom(b, a + b)
  }

  //The tricky part is computing this sequence without causing an infinite recursion.
  println(fibFrom(1, 1).take(7).toList)

}
