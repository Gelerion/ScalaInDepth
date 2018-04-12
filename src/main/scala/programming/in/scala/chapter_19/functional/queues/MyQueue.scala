package programming.in.scala.chapter_19.functional.queues

/**
  * Created by denis.shuvalov on 08/04/2018.
  *
  * Looking at these two examples, it does not seem easy to come up with an implementation that's constant time for
  * all three operations. In fact, it looks doubtful that this is even possible! However, by combining the two operations
  * you can get very close. The idea is to represent a queue by two lists, called leading and trailing. The leading
  * list contains elements towards the front, whereas the trailing list contains elements towards the back of the
  * queue in reversed order. The contents of the whole queue are at each instant equal to "leading ::: trailing.reverse".
  * *
  * Now, to append an element, you just cons it to the trailing list using the :: operator, so enqueue is constant time.
  * This means that, when an initially empty queue is constructed from successive enqueue operations, the trailing list
  * will grow whereas the leading list will stay empty. Then, before the first head or tail operation is performed on an
  * empty leading list, the whole trailing list is copied to leading, reversing the order of the elements. This is done
  * in an operation called mirror.
  */
class MyQueue[T](private val leading: List[T], private val trailing: List[T]) {
  /*
    scala> val q = Queue(1, 2, 3)
    q: Queue[Int] = Queue(1, 2, 3)

    scala> val q1 = q enqueue 4
    q1: Queue[Int] = Queue(1, 2, 3, 4)

    scala> q
    res0: Queue[Int] = Queue(1, 2, 3)
   */
  private def mirror =
    if(leading.isEmpty) new MyQueue(trailing.reverse, Nil)
    else this

  def head = mirror.leading.head
  def tail = new MyQueue(mirror.leading.tail, mirror.trailing)
  def enqueue(x: T) = new MyQueue(leading, x :: trailing)


  /*
  What is the complexity of this implementation of queues? The mirror operation might take time proportional to the
  number of queue elements, but only if list leading is empty. It returns directly if leading is non-empty. Because
  head and tail call mirror, their complexity might be linear in the size of the queue, too. However, the longer the
  queue gets, the less often mirror is called.

  Indeed, assume a queue of length n with an empty leading list. Then mirror has to reverse-copy a list of length n.
  However, the next time mirror will have to do any work is once the leading list is empty again, which will be the
  case after n tail operations. This means you can "charge" each of these n tail operations with one n'th of the
  complexity of mirror, which means a constant amount of work. Assuming that head, tail, and enqueue operations appear
  with about the same frequency, the amortized complexity is hence constant for each operation. So functional queues
  are asymptotically just as efficient as mutable ones.
   */

  /*
  Now, there are some caveats that need to be attached to this argument. First, the discussion was only about asymptotic
  behavior; the constant factors might well be somewhat different. Second, the argument rested on the fact that head,
  tail and enqueue are called with about the same frequency. If head is called much more often than the other two
  operations, the argument is not valid, as each call to head might involve a costly re-organization of the list with
  mirror. The second caveat can be avoided; it is possible to design functional queues so that in a sequence of
  successive head operations only the first one might require a re-organization.
   */
}
