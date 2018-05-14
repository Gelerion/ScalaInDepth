package programming.in.scala.chapter_32.execution.context

import scala.concurrent.Future
/**
  * Created by denis.shuvalov on 10/05/2018.
  *
  * Although not a silver bullet, Scala's Future offers one way to deal with concurrency that can reduce, and often
  * eliminate, the need to reason about shared data and locks. When you invoke a Scala method, it performs a computation
  * "while you wait" and returns a result. If that result is a Future, the Future represents another computation to be
  * performed asynchronously, often by a completely different thread. As a result, many operations on Future require an
  * implicit execution context that provides a strategy for executing functions asynchronously. For example, if you try
  * to create a future via the Future.apply factory method without providing an implicit execution context, an instance
  * of scala.concurrent.ExecutionContext, you'll get a compiler error
  */
object FutureExample {

  //Cannot find an implicit ExecutionContext.
  //      You might pass an (implicit ec: ExecutionContext)

  import scala.concurrent.ExecutionContext.Implicits.global

  val fut = Future {
    Thread.sleep(10000)
    21 + 21
  }

  fut.map(x => x + 1)

  fut.isCompleted

  //Note that the operations performed in this example—the future creation, the 21 + 21 sum calculation, and the 42 + 1
  // increment—may be performed by three different threads.
  fut.value //Option[scala.util.Try[Int]] = Some(Success(43))

  //Transforming
  val fut1 = Future { Thread.sleep(10000); 21 + 21 }
  val fut2 = Future { Thread.sleep(10000); 23 + 23 }
  //Given these two futures, you can obtain a new future representing the asynchronous sum of their results like this
  val res1: Future[Int] = for {
    x <- fut1
    y <- fut2
  } yield x + y

  //Because for expressions serialize their transformations, if you don't create the futures before the for expression,
  //they won't run in parallel.
//  for {
//    x <- Future { Thread.sleep(10000); 21 + 21 }
//    y <- Future { Thread.sleep(10000); 23 + 23 }
//  } yield x + y


}
