package programming.in.scala.chapter_32.async.programming.v2

import java.util.concurrent.atomic.AtomicReference

import programming.in.scala.chapter_32.async.programming.adt.{Start, State, WaitForA, WaitForB}
import programming.in.scala.chapter_32.async.programming.v1.CallbackHell._

import scala.annotation.tailrec

object ParallelExecution {

  //BAD
  def timesFourInParallel(n: Int): Async[Int] =
    onFinish => {
      var cacheA = 0

      timesTwo(n) { a => cacheA = a }
      timesTwo(n) { b => onFinish(cacheA + b) }
    }

  //Limbo of Nondeterminism
  /*timesFourInParallel(20) { result => println(s"Result: $result") }
  => Result: 80
  timesFourInParallel(20) { result => println(s"Result: $result") }
  => Result: 40
  */

  //This right here is an example showing nondeterminism in action. We get no ordering guarantees
  //about which one finishes first, so if we want parallel processing, we need to model a
  //mini state machine for doing synchronization.


  //Sill BAD
  def timesFourInParallelAdt(n: Int): Async[Int] =
    onFinish => {
      var state: State = Start

      timesTwo(n) { a =>
        state match {
          case Start => state = WaitForB(a)
          case WaitForA(b) => onFinish(a + b)
          case WaitForB(_) =>
            // Can't be caught b/c async, hopefully it gets reported
            throw new IllegalStateException(state.toString)
        }
      }
      timesTwo(n) { b =>
        state match {
          case Start => state = WaitForA(b)
          case WaitForB(a) => onFinish(a + b)
          case WaitForA(_) =>
            // Can't be caught b/c async, hopefully it gets reported
            throw new IllegalStateException(state.toString)
        }
      }
    }

  //But wait, we aren't over because the JVM has true 1:1 multi-threading, which means we get to enjoy
  //shared memory concurrency and thus access to that state has to be synchronized.
  /*
  // We need a common reference to act as our monitor
      val lock = new AnyRef
      var state: State = Start

      timesTwo(n) { a =>
        lock.synchronized {
          state match {
            case Start =>
              state = WaitForB(a)
            case WaitForA(b) =>
              onFinish(a + b)
            case WaitForB(_) =>
              // Can't be caught b/c async, hopefully it gets reported
              throw new IllegalStateException(state.toString)
          }
        }
      }
   */

  //Such high-level locks protect resources (such as our state) from being accessed in parallel by multiple threads.
  //But I personally prefer to avoid high-level locks because the kernel's scheduler can freeze any thread for any reason,
  //including threads that hold locks, freezing a thread holding a lock means that other threads will be unable to make
  //progress and if you want to guarantee constant progress (e.g. soft real-time characteristics),
  //then non-blocking logic is preferred when possible.

  //Correct Version for JVM
  def timesFourInParallelAdtValid(n: Int): Async[Int] =
    onFinish => {
      val state: AtomicReference[State] = new AtomicReference[State](Start)

      @tailrec def onValueA(a: Int): Unit =
        state.get match {
          case Start =>
            if (!state.compareAndSet(Start, WaitForB(a))) {
              onValueA(a) //retry
            }
          case WaitForA(b) => onFinish(a + b)
          case WaitForB(_) =>
            // Can't be caught b/c async, hopefully it gets reported
            throw new IllegalStateException(state.toString)
        }

      timesTwo(n)(onValueA)

      @tailrec def onValueB(b: Int): Unit =
        state.get match {
          case Start =>
            if (!state.compareAndSet(Start, WaitForA(b)))
              onValueB(b) // retry
          case WaitForB(a) =>
            onFinish(a + b)
          case WaitForA(_) =>
            // Can't be caught b/c async, hopefully it gets reported
            throw new IllegalStateException(state.toString)
        }

      timesTwo(n)(onValueB)
    }
}
