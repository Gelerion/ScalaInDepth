package programming.in.scala.advanced.cats.functional.case_study.testing_async_code.v2

import cats.Applicative
import cats.syntax.traverse._
import cats.instances.list._
import cats.syntax.functor._ // for map

import scala.concurrent.Future
import scala.language.higherKinds   // for Traverse

class UptimeServiceV2[F[_]](client: UptimeClientV2[F])(implicit applicative: Applicative[F]) {
  //class UptimeService[F[_]: Applicative](client: UptimeClient[F])

  def getTotalUptime(hostnames: List[String]): F[Int] = {
    /*
    without (implicit applicative: Applicative[F])
    hostnames.traverse(client.getUptime)

    The problem here is that traverse only works on sequences of values that have an Applicative.
    In our original code we were traversing a List[Future[Int]]. There is an applicative for Future so that was fine.
    In this version we are traversing a List[F[Int]]. We need to prove to the compiler that F has an Applicative
     */

    hostnames.traverse(client.getUptime).map(_.sum)
  }

//  def getTotalUptime(hostnames: List[String]): Future[Int] = {
//    hostnames.traverse(client.getUptime).map(_.sum)
//    //syntax.traverse.toTraverseOps(...)(implicit instances.list.catsStdInstancesForList)
//  }
}
