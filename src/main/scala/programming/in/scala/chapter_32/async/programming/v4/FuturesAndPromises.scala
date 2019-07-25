package programming.in.scala.chapter_32.async.programming.v4

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/*
The scala.concurrent.Future describes strictly evaluated asynchronous computations, being similar to our Async type used above.

Wikipedia says:
Future and Promise are constructs used for synchronizing program execution in some concurrent programming languages.
They describe an object that acts as a proxy for a result that is initially unknown, usually because the computation
of its value is yet incomplete.

Author's Rant:
The docs.scala-lang.org article on Futures and Promises currently says that
"Futures provide a way to reason about performing many operations in parallel– in an efficient and non-blocking way",
but that is misleading, a source of confusion.

The Future type describes asynchrony and not parallelism. Yes, you can do things in parallel with it,
but it's not meant only for parallelism (async != parallelism) and for people looking into ways to use their CPU capacity
to its fullest, working with Future can prove to be expensive and unwise, because in certain cases it has performance issues.
 */
object FuturesAndPromises {

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global

    timesTwo(20).onComplete { result => println(s"Times two. Result: $result") }
    timesFour(20).onComplete { result => println(s"Times four. Result: $result") }
    Thread.sleep(100)
  }

  def timesTwo(n: Int)(implicit ec: ExecutionContext): Future[Int] = Future(n * 2)

  /*
  Easy enough. That "for comprehension" magic right there is translated to nothing more than calls to
  flatMap and map, being literally equivalent with:
  def timesFour(n: Int)(implicit ec: ExecutionContext): Future[Int] =
  timesTwo(n).flatMap { a =>
    timesTwo(n).map { b =>
      a + b
    }
  }
   */
  def timesFour(n: Int)(implicit ec: ExecutionContext): Future[Int] =
    for (
      a <- timesTwo(n);
      b <- timesTwo(n)
    ) yield a + b

  //It can be a little confusing and it catches beginners off-guard. Because of its execution model,
  //in order to execute things in parallel, you simply have to initialize those future references before the composition happens.
  def timesFourInParallel(n: Int)(implicit ec: ExecutionContext): Future[Int] = {
    val fa = timesTwo(n)
    val fb = timesTwo(n)

    for(a <- fa; b <- fb) yield a + b
  }

  //An alternative would be to use Future.sequence, which works for arbitrary collections:
  def timesFourInParallel2(n: Int)(implicit ec: ExecutionContext): Future[Int] = {
    Future.sequence(timesTwo(n) :: timesTwo(n) :: Nil).map(_.sum)
  }
}

/*
The properties of Future:

 * Eagerly evaluated (strict and not lazy), meaning that when the caller of a function receives a Future reference,
   whatever asynchronous process that should complete it has probably started already.
 * Memoized (cached), since being eagerly evaluated means that it behaves like a normal value instead of a function
   and the final result needs to be available to all listeners. The purpose of the value property is to return that
   memoized result or None if it isn't complete yet. Goes without saying that calling its def value yields a non-deterministic result.
 * Streams a single result and it shows because of the memoization applied. So when listeners are registered for completion,
   they'll only get called once at most.

Explanatory notes about the ExecutionContext:

 * The ExecutionContext manages asynchronous execution and although you can view it as a thread-pool, it's not
   necessarily a thread-pool (because async != multithreading or parallelism).
 * The onComplete is basically our Async type defined above, however it takes an ExecutionContext because
   all completion callbacks need to be called asynchronously.
 * All combinators and utilities are built on top of onComplete, therefore all combinators and utilities
   must also take an ExecutionContext parameter.
 */
trait MyFuture[+T] {
  def value: Option[Try[T]]

  def onComplete(f: Try[T] => Unit)(implicit ec: ExecutionContext): Unit

  def map[U](f: T => U)(implicit ec: ExecutionContext): MyFuture[U]

  //sequencing
  def flatMap[U](f: T => MyFuture[U])(implicit ec: ExecutionContext): MyFuture[U]
}