package programming.in.scala.chapter_12

import scala.collection.mutable.ArrayBuffer

/**
  * Created by denis.shuvalov on 14/03/2018.
  *
  * Traits let you modify the methods of a class, and they do so in a way that allows you to stack
  * those modifications with each other.
  */
object StackableModifications extends App {

  /*
  As an example, consider stacking modifications to a queue of integers. The queue will have two operations: put,
  which places integers in the queue, and get, which takes them back out. Queues are first-in, first-out, so get
  should return the integers in the same order they were put in the queue.

  Given a class that implements such a queue, you could define traits to perform modifications such as these:

   * Doubling: double all integers that are put in the queue
   * Incrementing: increment all integers that are put in the queue
   * Filtering: filter out negative integers from a queue

  These three traits represent modifications, because they modify the behavior of an underlying queue class rather
  than defining a full queue class themselves. The three are also stackable. You can select any of the three you like,
  mix them into a class, and obtain a new class that has all of the modifications you chose.
   */

  val queue = new MyQueue
  queue.put(10)
  println(s"Putting 10 into the doubling queue and getting ${queue.get()}")
  println("------------------------------")

  //The order of mixins is significant
  val composedQueue = new BasicIntQueue with Incrementing with Filtering
  composedQueue.put(-1); composedQueue.put(0); composedQueue.put(1)

  //roughly speaking, traits further to the right take effect first
  println(s"Composed queue contains: ${composedQueue.get()}") //1
  println(s"Composed queue contains: ${composedQueue.get()}") //2
}

abstract class IntQueue {
  def get(): Int
  def put(x: Int)
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]

  def get(): Int = buf.remove(0)

  def put(x: Int): Unit = { buf += x }
}

//The Doubling trait has two funny things going on. The first is that it declares a superclass, IntQueue.
//This declaration means that the trait can only be mixed into a class that also extends IntQueue.
//Thus, you can mix Doubling into BasicIntQueue, but not into Rational
trait Doubling extends IntQueue { //The Doubling stackable modification trait

  //To tell the compiler you are doing this on purpose, you must mark such methods as abstract override. This combination
  //of modifiers is only allowed for members of traits, not classes, and it means that the trait must be mixed into
  //some class that has a concrete definition of the method in question.
  abstract override def put(x: Int): Unit = { super.put(2 * x) }

}
//The second funny thing is that the trait has a super call on a method declared abstract. Such calls are illegal
//for normal classes because they will certainly fail at run time. For a trait, however, such a call can actually
//succeed. Since super calls in a trait are dynamically bound, the super call in trait Doubling will work so long as
//the trait is mixed in after another trait or class that gives a concrete definition to the method.

class MyQueue extends BasicIntQueue with Doubling

trait Incrementing extends IntQueue {
  abstract override def put(x: Int) = { super.put(x + 1) }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int) = {
    if (x >= 0) super.put(x)
  }
}