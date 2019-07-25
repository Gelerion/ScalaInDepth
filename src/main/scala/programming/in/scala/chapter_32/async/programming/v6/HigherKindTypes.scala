package programming.in.scala.chapter_32.async.programming.v6

import scala.language.higherKinds
import scala.concurrent._

object HigherKindTypes {
  import FutureMonad.instance
  import scala.concurrent.ExecutionContext.Implicits.global

  def main(args: Array[String]): Unit = {
    fib[Future](40).foreach(r => print(s"Result $r"))
  }

  //This is really powerful stuff. We can now describe a generic function that works with Task,
  //Future, IO, whatever, although it would be great if the flatMap operation is stack-safe:
  def fib[F[_]](n: Int)(implicit F: Monad[F]): F[BigInt] = {
    def loop(n: Int, a: BigInt, b: BigInt): F[BigInt] = {
      F.flatMap(F.pure(n)) { n =>
        if (n <= 1) F.pure(b)
        else loop(n - 1, b, a + b)
      }
    }

    loop(n, 0, 1)
  }

  //So we can now define generic functions based on Applicative which is going to work for Future, Task, etc:
  def sequence[F[_], A](list: List[F[A]])(implicit F: Applicative[F]): F[List[A]] = {
    val seed = F.pure(List.empty[A])
    val r = list.foldLeft(seed)((acc, e) => F.map2(acc, e)((l, a) => a :: l))
    F.map(r)(_.reverse)
  }
}

trait Monad[F[_]] {
  /** Constructor (said to lift a value `A` in the `F[A]`
    * monadic context). Also part of `Applicative`, see below.
    */
  def pure[A](a: A): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

class FutureMonad(implicit ec: ExecutionContext) extends Monad[Future] {

  override def pure[A](a: A): Future[A] = Future.successful(a)

  override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)
}

object FutureMonad {
  implicit def instance(implicit ec: ExecutionContext): FutureMonad = new FutureMonad
}

// -- Monads define sequencing of operations, but sometimes we want to compose the results of computations
//    that are independent of each other, that can be evaluated at the same time, possibly in parallel.
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  /** Constructor (lifts a value `A` in the `F[A]` applicative context). */
  def pure[A](a: A): F[A]

  /** Maps over two references at the same time.
    *
    * In other implementations the applicative operation is `ap`,
    * but `map2` is easier to understand.
    */
  def map2[A,B,R](fa: F[A], fb: F[B])(f: (A,B) => R): F[R]
}

trait MonadV2[F[_]] extends Applicative[F] {
  def flatMap[A,B](fa: F[A])(f: A => F[B]): F[B]
}

class FutureMonadV2(implicit ec: ExecutionContext) extends Monad[Future] {
  def pure[A](a: A): Future[A] =
    Future.successful(a)

  def flatMap[A,B](fa: Future[A])(f: A => Future[B]): Future[B] =
    fa.flatMap(f)

  def map2[A,B,R](fa: Future[A], fb: Future[B])(f: (A,B) => R): Future[R] =
  // For Future there's no point in supplying an implementation that's
  // not based on flatMap, but that's not the case for Task ;-)
    for (a <- fa; b <- fb) yield f(a,b)
}

object FutureMonadV2 {
  implicit def instance(implicit ec: ExecutionContext): FutureMonadV2 =
    new FutureMonadV2
}