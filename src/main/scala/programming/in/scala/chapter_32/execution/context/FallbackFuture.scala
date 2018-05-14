package programming.in.scala.chapter_32.execution.context

import scala.concurrent.Future

/**
  * Created by denis.shuvalov on 13/05/2018.
  *
  * Dealing with failure: failed, fallBackTo, recover, and recoverWith
  */
object FallbackFuture {
  import scala.concurrent.ExecutionContext.Implicits.global

  val failure = Future { 42 / 0 }

  failure.value //Some(Failure(java.lang.ArithmeticException: / by zero))

  val expectedFailure = failure.failed //scala.concurrent.Future[Throwable] = ...

  expectedFailure.value //Option[scala.util.Try[Throwable]] = Some(Success(java.lang.ArithmeticException: / by zero))

  //The fallbackTo method allows you to provide an alternate future to use in case the future on which you invoke fallbackTo fails.
  val success = Future { 42 / 1 }

  failure.fallbackTo(success)

  //If the original future on which fallbackTo is invoked fails, a failure of the future passed to fallbackTo is
  //essentially ignored. The future returned by fallbackTo will fail with the initial exception.
  val failedFallback = failure.fallbackTo(
    Future { val res = 42; require(res < 0); res }
  )
  //Option[scala.util.Try[Int]] = Some(Failure(java.lang.ArithmeticException: / by zero))

  val recovered = failedFallback recover {
    case ex: ArithmeticException => -1
  }

  //Mapping both possibilities: transform
  val first = success.transform(
    res => res * -1,
    ex => new Exception("see cause", ex)
  )
}
