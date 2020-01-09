package programming.in.scala.advanced.cats.functional.foldable_and_traverse

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object TraversingWithFutures {
  /*
  We can demonstrate Traverse using the Future.traverse and Future.sequence methods in the Scala standard library.
  These methods provide Future-specific implementations of the traverse pattern. As an example, suppose
  we have a list of server hostnames and a method to poll a host for its uptime:
   */

  val hostnames = List(
    "alpha.example.com",
    "beta.example.com",
    "gamma.demo.com"
  )

  def getUptime(hostname: String): Future[Int] = Future(hostname.length * 60) // just for demonstration

  val allUptimes: Future[List[Int]] =
    hostnames.foldLeft(Future(List.empty[Int])) {
      (accum, host) =>
        val uptime: Future[Int] = getUptime(host)
        for {
          accum <- accum
          uptime <- uptime
        } yield accum :+ uptime
    }

  /*
  Intuitively, we iterate over hostnames, call func for each item, and combine the results into a list.
  This sounds simple, but the code is fairly unwieldy because of the need to create and combine Futures at every iteration.
  We can improve on things greatly using Future.traverse, which is tailor-made for this pattern:
   */
  val allUptimes2: Future[List[Int]] = Future.traverse(hostnames)(getUptime)
  //Await.result(allUptimes, 1.second)
  // List[Int] = List(1020, 960, 840)

}
