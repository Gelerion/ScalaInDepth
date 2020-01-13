package programming.in.scala.advanced.cats.functional.case_study.testing_async_code.test

import programming.in.scala.advanced.cats.functional.case_study.testing_async_code.UptimeClient

import scala.concurrent.Future

class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient {
  def getUptime(hostname: String): Future[Int] = Future.successful(hosts.getOrElse(hostname, 0))
}


