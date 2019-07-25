package programming.in.scala.chapter_32.async.programming.v5

import scala.concurrent.{ExecutionContext, Future}

/*
The Future type is entirely safe for recursive operations (because of the reliance on the ExecutionContext for executing callbacks)
 */
object Recursivity {

  def main(args: Array[String]): Unit = {
    //And this time we get no StackOverflowError:
//    val list = 0.until(10000).map(timesTwo).toList
//    sequence(list).foreach(r => println(s"Sum: ${r.sum}"))
  }

  def mapBoth[A,B,R](fa: Future[A], fb: Future[B])(f: (A, B) => R)
                    (implicit ec: ExecutionContext): Future[R] = {
    for (a <- fa; b <- fb) yield f(a,b)
  }

  def sequence[A](list: List[Future[A]])(implicit ec: ExecutionContext): Future[List[A]] = {
    val seed = Future.successful(List.empty[A])
    list.foldLeft(seed)((acc,f) => for (l <- acc; a <- f) yield a :: l)
      .map(_.reverse)
  }
}
