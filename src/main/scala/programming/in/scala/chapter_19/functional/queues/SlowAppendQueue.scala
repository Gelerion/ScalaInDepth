package programming.in.scala.chapter_19.functional.queues

/**
  * Created by denis.shuvalov on 08/04/2018.
  *
  * The problem with this implementation is in the enqueue operation. It takes time proportional
  * to the number of elements stored in the queue.
  */
class SlowAppendQueue[T](elems: List[T]) {
  def head = elems.head
  def tail = elems.tail
  def enqueue(x: T) = new SlowAppendQueue(elems ::: List(x))

}
