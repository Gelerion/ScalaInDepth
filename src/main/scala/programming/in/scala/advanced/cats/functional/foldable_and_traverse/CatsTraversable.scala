package programming.in.scala.advanced.cats.functional.foldable_and_traverse

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object CatsTraversable {
  /*
  Future.traverse and Future.sequence solve a very specific problem: they allow us to iterate over a sequence of
  Futures and accumulate a result. The simplified examples above only work with Lists, but the real
  Future.traverse and Future.sequence work with any standard Scala collection.

  Cats’ Traverse type class generalises these patterns to work with any type of Applicative: Future, Option,
  Validated, and so on. We’ll approach Traverse in the next sections in two steps: first we’ll generalise
  over the Applicative, then we’ll generalise over the sequence type. We’ll end up with an extremely valuable
  tool that trivialises many operations involving sequences and other data types.
   */

  val hostnames = List(
    "alpha.example.com",
    "beta.example.com",
    "gamma.demo.com"
  )
  def main(args: Array[String]): Unit = {
    import cats.instances.future._   // for Applicative
    import scala.concurrent.duration._

    val totalUptime: Future[List[Int]] = listTraverse(hostnames)(getUptime)
    println(Await.result(totalUptime, 1.second)) //List(1020, 960, 840)

    import cats.instances.vector._ // for Applicative
    val vector: Vector[List[Int]] = listSequence(List(Vector(1, 2), Vector(3, 4)))
    //output?
    //Vector is a monad, so its semigroupal combine function is based on flatMap
    //We’ll end up with a Vector of Lists of all the possible combinations of List(1, 2) and List(3, 4):
    //Vector(List(1, 3), List(1, 4), List(2, 3), List(2, 4))

    import cats.Traverse
    import cats.instances.future._ // for Applicative
    import cats.instances.list._   // for Traverse
    val totalUptime2: Future[List[Int]] = Traverse[List].traverse(hostnames)(getUptime)

    import cats.syntax.traverse._ // for sequence and traverse
    Await.result(hostnames.traverse(getUptime), 1.second)
  }

  def getUptime(hostname: String): Future[Int] = Future(hostname.length * 60) // just for demonstration

  def oldCombine(accum : Future[List[Int]], host  : String): Future[List[Int]] = {
    val uptime = getUptime(host)
    for {
      accum  <- accum
      uptime <- uptime
    } yield accum :+ uptime
  }

  //equivalent to Semigroupal.combine:
  import cats.Applicative
  import cats.instances.future._   // for Applicative
  import cats.syntax.applicative._ // for pure
  import cats.syntax.apply._ // for mapN

  def newCombine(accum: Future[List[Int]], host: String): Future[List[Int]] =
    (accum, getUptime(host)).mapN(_ :+ _)

  //By substituting these snippets back into the definition of traverse we can generalise it to to work with any Applicative:
  import scala.language.higherKinds
  import cats.Applicative
  import cats.syntax.applicative._ // for pure


  def listTraverse[F[_]: Applicative, A, B](list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
      (accum, func(item)).mapN(_ :+ _)
    }

  def listSequence[F[_]: Applicative, B](list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)

  /*
  Our listTraverse and listSequence methods work with any type of Applicative, but they only work with one
  type of sequence: List. We can generalise over different sequence types using a type class, which brings
  us to Cats’ Traverse. Here’s the abbreviated definition:

  trait Traverse[F[_]] {
      def traverse[G[_]: Applicative, A, B](inputs: F[A])(func: A => G[B]): G[F[B]]
      def sequence[G[_]: Applicative, B](inputs: F[G[B]]): G[F[B]] = traverse(inputs)(identity)
    }
   */
}
