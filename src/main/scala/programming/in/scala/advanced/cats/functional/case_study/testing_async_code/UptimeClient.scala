package programming.in.scala.advanced.cats.functional.case_study.testing_async_code

import scala.concurrent.Future

trait UptimeClient {
  //We’re measuring the uptime on a set of servers.
  def getUptime(hostname: String): Future[Int]
}
