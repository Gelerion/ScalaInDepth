package programming.in.scala.advanced.cats.functional.case_study.testing_async_code.v2

import cats.Id

import scala.concurrent.Future
import scala.language.higherKinds

//Abstracting over Type Constructors
trait UptimeClientV2[F[_]] {

  //what result type should we give to this abstract method?
  //def getUptime(hosts: String): ???


  //At first this may seem difficult. We want to retain the Int part from each type but “throw away” the Future
  //part in the test code. Fortunately, Cats provides a solution in terms of the identity type, Id.
  //Id allows us to “wrap” types in a type constructor without changing their meaning

  def getUptime(hostname: String): F[Int]
}

/*
We need to implement two versions of UptimeClient: an asynchronous one for use in production and a synchronous one for use in our unit tests:

trait RealUptimeClient extends UptimeClient {
  def getUptime(hostname: String): Future[Int]
}

trait TestUptimeClient extends UptimeClient {
  def getUptime(hostname: String): Int
}
 */

trait RealUptimeClientV2 extends UptimeClientV2[Future] {
  def getUptime(hostname: String): Future[Int]
}

//Note that, because Id[A] is just a simple alias for A, we don’t need to refer to the type in
//TestUptimeClient as Id[Int]—we can simply write Int instead:
class TestUptimeClientV2(hosts: Map[String, Int]) extends UptimeClientV2[Id] {
  def getUptime(hostname: String): Int = hosts.getOrElse(hostname, 0)
}