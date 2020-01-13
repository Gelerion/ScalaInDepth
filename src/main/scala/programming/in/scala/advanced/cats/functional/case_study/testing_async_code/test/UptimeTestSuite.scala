package programming.in.scala.advanced.cats.functional.case_study.testing_async_code.test

import programming.in.scala.advanced.cats.functional.case_study.testing_async_code.UptimeService

object UptimeTestSuite {

  def main(args: Array[String]): Unit = {

  }

  //How to test async code?

  // First approach, error prone
/*  def testTotalUptime() = {
    val hosts    = Map("host1" -> 10, "host2" -> 6)
    val client   = new TestUptimeClient(hosts)
    val service  = new UptimeService(client)
    val actual   = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum
    assert(actual == expected) //!! actual is of type Future[Int] not Int, we can't compare them
  }*/

}
