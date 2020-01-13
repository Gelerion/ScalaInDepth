package programming.in.scala.advanced.cats.functional.case_study.testing_async_code.v2

object UptimeTestSuiteV2 {

  def main(args: Array[String]): Unit = {
    testTotalUptime()
  }

  //How to test async code?

  // First approach, error prone
  def testTotalUptime(): Unit = {
    val hosts    = Map("host1" -> 10, "host2" -> 6)
    val client   = new TestUptimeClientV2(hosts)
    val service  = new UptimeServiceV2(client)
    val actual   = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum
    assert(actual == expected)
  }

}
