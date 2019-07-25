package programming.in.scala.chapter_32.async.programming.v3

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.ExecutionContext.global
import scala.math.Integral.Implicits._
import scala.annotation.tailrec

object Recursivity {

  //Invocation
  def main(args: Array[String]): Unit = {
    sequence(List(timesTwo(10), timesTwo(20), timesTwo(30))) { r =>
      println(s"Result $r")
    }

    Thread.sleep(100)

    //Oh, you really think we are done?
    //-----------------------------------------------
    //val list = 0.until(10000).map(timesTwo).toList
    //sequence(list)(r => println(s"Sum: ${r.sum}"))
    //-----------------------------------------------
    // => java.lang.StackOverflowError

    /*
    onFinish call being made without a forced async boundary can lead to a stack-overflow error.
    On top of Javascript this can be solved by scheduling it with setTimeout
    and on top of the JVM you need a thread-pool or a Scala ExecutionContext
     */
  }

  type Async[+A] = (A => Unit) => Unit

  //more generic way
  //https://danielwestheide.com/blog/2013/02/06/the-neophytes-guide-to-scala-part-12-type-classes.html
  def timesTwo[A : Integral](a: A): Async[A] =
    onFinish => {
      global.execute(() => {
        val result = a * 2.asInstanceOf[A]
        onFinish(result)
      })
    }

  def timesTwo(a: Int): Async[Int] =
    onFinish => {
      global.execute(() => {
        val result = a * 2
        onFinish(result)
      })
    }

  def mapBoth[A,B,R](fa: Async[A], fb: Async[B])(f: (A, B) => R): Async[R] = {
    sealed trait State[+A, +B]
    object Start extends State[Nothing, Nothing]
    final case class WaitForA[+B](b: B) extends State[Nothing, B]
    final case class WaitForB[+A](a: A) extends State[A, Nothing]

    onFinish => {
      val state = new AtomicReference[State[A, B]](Start)

      @tailrec def onValueA(a: A): Unit = state.get match {
        case Start =>
          if (!state.compareAndSet(Start, WaitForB(a)))
            onValueA(a) //retry
        case WaitForA(b) =>
          onFinish(f(a, b))
        case WaitForB(_) =>
          // Can't be caught b/c async, hopefully it gets reported
          throw new IllegalStateException(state.toString)
      }

      @tailrec def onValueB(b: B): Unit = state.get match {
        case Start =>
          if (!state.compareAndSet(Start, WaitForA(b)))
            onValueB(b) //retry
        case WaitForB(a) =>
          onFinish(f(a, b))
        case WaitForA(_) =>
          // Can't be caught b/c async, hopefully it gets reported
          throw new IllegalStateException(state.toString)
      }

      fa(onValueA)
      fb(onValueB)
    }
  }

  //And now we can define an operation similar to Scala's Future.sequence
  def sequence[A](list: List[Async[A]]): Async[List[A]] = {
    @tailrec def loop(list: List[Async[A]], acc: Async[List[A]]): Async[List[A]] = {
      list match {
        case Nil =>
          onFinish => acc(r => onFinish(r.reverse))
        case x :: xs =>
          val update = mapBoth(x, acc)(_ :: _)
          loop(xs, update)
      }
    }

    val empty: Async[List[A]] = _(Nil)
    loop(list, empty)
  }
}
