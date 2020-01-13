package programming.in.scala.advanced.cats.functional.case_study.testing_async_code

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import cats.instances.future._ // for Applicative pure
import cats.syntax.traverse._  // for traverse
import cats.instances.list._   // for Traverse

class UptimeService(client: UptimeClient) {

  def getTotalUptime(hostnames: List[String]): Future[Int] = {
    hostnames.traverse(client.getUptime).map(_.sum)
    //syntax.traverse.toTraverseOps(...)(implicit instances.list.catsStdInstancesForList)
  }

}
