package programming.in.scala.advanced.cats.functional.monad_transformers.exercise

import scala.concurrent.{Await, Future}
import cats.data.EitherT
import cats.instances.list._
import cats.syntax.applicative._
import cats.syntax.either._
import cats.instances.future._
import cats.syntax.flatMap._
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * The Autobots, well-known robots in disguise, frequently send messages during battle requesting the power levels
  * of their team mates. This helps them coordinate strategies and launch devastating attacks.
  */
object CatsAutobot {
//  type Response[A] = Future[Either[String, A]]
  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz"      -> 6,
    "Bumblebee" -> 8,
    "Hot Rod"   -> 10
  )

  def getPowerLevel(ally: String): Response[Int] =
    powerLevels.get(ally) match {
      case Some(value) => EitherT.right(Future(value))
      case None => EitherT.left(Future(s"$ally unreachable"))
    }

  //Two autobots can perform a special move if their combined power level is greater than 15.
  //Write a second method, canSpecialMove, that accepts the names of two allies and checks whether a special move is possible.
  //If either ally is unavailable, fail with an appropriate error message:
  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for {
      power1 <- getPowerLevel(ally1)
      power2 <- getPowerLevel(ally2)
    } yield (power1 + power2) > 15

  //Finally, write a method tacticalReport that takes two ally names and prints a message saying whether they can perform a special move:
  def tacticalReport(ally1: String, ally2: String): String = {
    val stack = canSpecialMove(ally1, ally2).value

    Await.result(stack, 1.second) match {
      case Left(msg) =>  s"Comms error: $msg"
      case Right(true)  => s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) => s"$ally1 and $ally2 need a recharge."
    }
  }

  def main(args: Array[String]): Unit = {
    println(tacticalReport("Jazz", "Bumblebee"))
    // res28: String = Jazz and Bumblebee need a recharge.

    println(tacticalReport("Bumblebee", "Hot Rod"))
    // res29: String = Bumblebee and Hot Rod are ready to roll out!

    println(tacticalReport("Jazz", "Ironhide"))
    // res30: String = Comms error: Ironhide unreachable
  }

  /*
  Each call to value unpacks a single monad transformer. We may need more than one call to completely unpack a large stack. For example, to Await the FutureEitherOption stack above, we need to call value twice:

  futureEitherOr
  // res14: FutureEitherOption[Int] = OptionT(EitherT(Future(Success(Right(Some(42))))))

  val intermediate = futureEitherOr.value
  // intermediate: FutureEither[Option[Int]] = EitherT(Future(Success(Right(Some(42)))))

  val stack = intermediate.value
  // stack: scala.concurrent.Future[Either[String,Option[Int]]] = Future(Success(Right(Some(42))))

  Await.result(stack, 1.second)
  // res15: Either[String,Option[Int]] = Right(Some(42))
   */
}
