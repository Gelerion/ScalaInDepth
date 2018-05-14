package programming.in.scala.chapter_32.execution.context

import scala.concurrent.Future

/**
  * Created by denis.shuvalov on 14/05/2018.
  *
  * Combining futures: zip, Future.fold, Future.reduce, Future.sequence, and Future.traverse
  */
object CombiningFutures extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  val success  = Future { 42 / 1 }
  val failure = Future { 42 / 0 }

  val failedFallback = failure.fallbackTo(
    Future { val res = 42; require(res < 0); res }
  )
  println(s"Failed fallback ${failedFallback.value}")

  val recovered = failure recover {
    case ex: ArithmeticException => -1
    case _ => 5
  }

  println(s"Recovered fallback ${recovered.value}")

  val zippedSuccess = success zip recovered //Future[(Int, Int)]
  println(zippedSuccess.value) //Some(Success((42,-1))

  //If either of the futures fail, however, the future returned by zip will also fail with the same exception:
  val zippedFailure = success zip failure
}
