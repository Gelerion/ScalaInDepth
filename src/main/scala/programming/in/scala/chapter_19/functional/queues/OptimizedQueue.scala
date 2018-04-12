package programming.in.scala.chapter_19.functional.queues

/**
  * Created by denis.shuvalov on 09/04/2018.
  *
  * You might wonder whether this code passes the Scala type checker. After all, queues now contain two reassignable
  * fields of the covariant parameter type T. Is this not a violation of the variance rules? It would be indeed, except
  * for the detail that leading and trailing have a private[this] modifier, and are thus declared to be object private.
  *
  * It turns out that accesses to variables from the same object in which they are defined do not cause problems with variance.
  * The intuitive explanation is that, in order to construct a case where variance would lead to type errors, you need to
  * have a reference to a containing object that has a statically weaker type than the type the object was defined with.
  * For accesses to object private values, however, this is impossible.
  */
class OptimizedQueue[+T](private[this] var leading: List[T],
                         private[this] var trailing: List[T]) {

  //The MyQueue class seen so far has a problem in that the mirror operation will repeatedly copy the trailing
  //into the leading list if head is called several times in a row on a list where leading is empty.
  //The wasteful copying could be avoided by adding some judicious side effects
  //This class presents a new implementation of Queue, which performs at most one trailing to leading adjustment
  //for any sequence of head operations
  private def mirror() = {
    if(leading.isEmpty) {
      leading = (leading /: trailing) { (xs, x) => x :: xs }
//      while (trailing.nonEmpty) {
//        leading = trailing.head :: leading
//        trailing = trailing.tail
//      }
    }
  }
  //What's different with respect to the previous version is that now leading and trailing are reassignable variables,
  //and mirror performs the reverse copy from trailing to leading as a side effect on the current queue instead of
  //returning a new queue. This side effect is purely internal to the implementation of the Queue operation; since
  //leading and trailing are private variables, the effect is not visible to clients of Queue.
  //So the new version of Queue still defines purely functional objects, in spite of the fact that they now contain reassignable fields.

  def head: T = {
    mirror()
    leading.head
  }

  def tail: OptimizedQueue[T] = {
    mirror()
    new OptimizedQueue(leading.tail, trailing)
  }

  def enqueue[U >: T](x: U) = new OptimizedQueue[U](leading, x :: trailing)

}

object OptimizedQueue {
  def apply[T](xs: T*) = new OptimizedQueue(xs.toList, Nil)
}

object TestOptimizedQueue extends App {
  val queue = OptimizedQueue(1).enqueue(2).enqueue(3).enqueue(4)
  println(queue.tail.tail.tail.head)

}