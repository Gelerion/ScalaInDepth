package programming.in.scala.chapter_32.execution.context

import scala.concurrent.{Future, Promise}

/**
  * Created by denis.shuvalov on 13/05/2018.
  *
  * Creating the Future: Future.failed, Future.successful, Future.fromTry, and Promises
  */
object CreatingFeature {
  import scala.concurrent.ExecutionContext.Implicits.global

  //Besides the apply method, used in earlier examples to create futures, the Future companion object also includes
  //three factory methods for creating already-completed futures: successful, failed, and fromTry. These factory
  //methods do not require an ExecutionContext.

  Future.successful { 21 + 21 }

  Future.failed(new Exception("bummer!"))

  import scala.util.{Success, Failure}
  Future.fromTry(Success {21 + 21})
  Future.fromTry(Failure(new Exception("bummer!")))

  //The most general way to create a future is to use a Promise. Given a promise you can obtain a future that is
  //controlled by the promise. The future will complete when you complete the promise. Here's an example:
  val pro = Promise[Int]
  val fut = pro.future
  val int = fut.value

  //You can complete the promise with methods named success, failure, and complete.
  pro.success(42)

  //Because Future also offers a withFilter method, you can perform the same operation with for expression filters:
  val valid = for (res <- fut if res > 0) yield res

  //Future's collect method allows you to validate the future value and transform it in one operation. If the partial
  //function passed to collect is defined at the future result, the future returned by collect will succeed with that
  //value transformed by the function:
  fut collect {case res if res > 0 => res + 46}
}
