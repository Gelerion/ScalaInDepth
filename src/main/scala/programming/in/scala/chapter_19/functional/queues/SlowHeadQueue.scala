package programming.in.scala.chapter_19.functional.queues

/**
  * Created by denis.shuvalov on 08/04/2018.
  * Now enqueue is constant time, but head and tail are not. They now take time proportional
  * to the number of elements stored in the queue.
  */
class SlowHeadQueue[T](elem: List[T]) {
  def head = elem.last
  def tail = new SlowHeadQueue(elem.init)
  def enqueue(x: T) = new SlowHeadQueue(x :: elem)

}
